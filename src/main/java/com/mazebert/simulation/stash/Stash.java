package com.mazebert.simulation.stash;

import com.mazebert.java8.Consumer;
import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.listeners.OnCardAdded;
import com.mazebert.simulation.listeners.OnCardRemoved;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.*;

public abstract strictfp class Stash<T extends Card> implements ReadonlyStash<T>, Hashable {
    private static final ItemLevelComparator ITEM_LEVEL_COMPARATOR = new ItemLevelComparator();

    private final List<StashEntry<T>> entries = new ArrayList<>();
    private final Map<Object, StashEntry<T>> entryByType;
    private final Map<Object, T> droppedUniques;
    private final Map<Object, Integer> autoTransmutes;
    private EnumMap<Rarity, CardType<T>[]> cardByDropRarity;
    private EnumMap<Rarity, CardType<T>[]> cardByDropRarityIncludingEldritchCards;
    private EnumMap<Rarity, CardType<T>[]> cardByDropRarityExcludingSupporterCards;

    private final OnCardAdded onCardAdded = new OnCardAdded();
    private final OnCardRemoved onCardRemoved = new OnCardRemoved();

    public int transmutedCommons;
    public int transmutedUncommons;
    public int transmutedRares;

    private transient int lastViewedIndex;

    protected Stash(Map<Object, StashEntry<T>> entryByType, Map<Object, T> uniques, Map<Object, Integer> autoTransmutes) {
        this.entryByType = entryByType;
        this.droppedUniques = uniques;
        this.autoTransmutes = autoTransmutes;
        updateCardByDropRarity();
    }

    protected void updateCardByDropRarity() {
        cardByDropRarity = populateCardByDropRarity(this::isDropable);
        cardByDropRarityIncludingEldritchCards = populateCardByDropRarity(c -> isDropable(c) || c.isEldritch(), this::increaseEldritchDropChance);
        cardByDropRarityExcludingSupporterCards = populateCardByDropRarity(c -> isDropable(c) && !c.isSupporterReward());
    }

    private EnumMap<Rarity, CardType<T>[]> populateCardByDropRarity(Predicate<T> predicate) {
        return populateCardByDropRarity(predicate, null);
    }

    @SuppressWarnings("unchecked")
    private EnumMap<Rarity, CardType<T>[]> populateCardByDropRarity(Predicate<T> predicate, Consumer<List<CardType<T>>> postProcessor) {
        EnumMap<Rarity, List<CardType<T>>> temp = new EnumMap<>(Rarity.class);

        for (Rarity rarity : Rarity.VALUES) {
            temp.put(rarity, new ArrayList<>());
        }

        for (CardType<T> possibleDrop : getPossibleDrops()) {
            T card = possibleDrop.instance();
            if (predicate.test(card)) {
                temp.get(card.getDropRarity()).add(possibleDrop);
            }
        }

        if (postProcessor != null) {
            for (Rarity rarity : Rarity.VALUES) {
                postProcessor.accept(temp.get(rarity));
            }
        }

        EnumMap<Rarity, CardType<T>[]> result = new EnumMap<>(Rarity.class);
        for (Rarity rarity : Rarity.VALUES) {
            List<CardType<T>> drops = temp.get(rarity);
            //noinspection Java8ListSort // For android 19 compatibility
            Collections.sort(drops, ITEM_LEVEL_COMPARATOR);
            result.put(rarity, drops.toArray(new CardType[0]));
        }
        return result;
    }

    private void increaseEldritchDropChance(List<CardType<T>> drops) {
        List<CardType<T>> eldritchCards = new ArrayList<>();

        for (CardType<T> drop : drops) {
            if (drop.instance().isEldritch()) {
                eldritchCards.add(drop);
            }
        }

        if (eldritchCards.isEmpty()) {
            return;
        }

        int regularCardCount = drops.size() - eldritchCards.size();
        int eldritchCardCount = eldritchCards.size();

        if (eldritchCardCount >= regularCardCount) {
            return;
        }

        int extraEldritchCards = (regularCardCount - eldritchCardCount) / eldritchCardCount;
        for (CardType<T> eldritchCard : eldritchCards) {
            for (int i = 0; i < extraEldritchCards; ++i) {
                drops.add(eldritchCard);
            }
        }
    }

    protected boolean isDropable(T card) {
        return card.isDropable();
    }

    public void add(CardType<T> cardType) {
        add(cardType, false);
    }

    public void add(CardType<T> cardType, boolean notify) {
        StashEntry<T> entry = get(cardType);
        if (entry == null) {
            entry = createEntry(cardType);
            entries.add(entry);
            entryByType.put(cardType, entry);
        } else {
            ++entry.amount;
        }

        if (notify) {
            dispatchCardAdded(cardType);
        }
    }

    public void add(CardType<T> cardType, int index, boolean notify) {
        StashEntry<T> entry = get(cardType);
        if (entry == null) {
            if (index > size()) {
                index = size() - 1;
            }
            entry = createEntry(cardType);
            entries.add(index, entry);
            entryByType.put(cardType, entry);
        } else {
            ++entry.amount;

            int oldIndex = getIndex(cardType);
            if (index >= size()) {
                index = size() - 1;
            }
            StashEntry<T> oldEntry = get(index);
            entries.set(oldIndex, oldEntry);
            entries.set(index, entry);
        }

        if (notify) {
            dispatchCardAdded(cardType);
        }
    }

    private void dispatchCardAdded(CardType<T> cardType) {
        // Auto transmute cards are instantly removed afterwards anyways!
        if (!isAutoTransmute(cardType)) {
            onCardAdded.dispatch(cardType);
        }
    }

    public T remove(CardType<T> cardType) {
        return remove(cardType, true, false);
    }

    public T remove(CardType<T> cardType, boolean newInstance, boolean automatic) {
        StashEntry<T> entry = get(cardType);
        if (entry != null) {
            --entry.amount;
            if (entry.amount <= 0) {
                entries.remove(entry);
                entryByType.remove(cardType);
            }
            onCardRemoved.dispatch(cardType, entry.amount, automatic);
            if (newInstance) {
                return createCard(entry);
            } else {
                return entry.card;
            }
        }
        return null;
    }

    public T transferTo(Stash<T> other, CardType<T> cardType) {
        T card = remove(cardType, false, false);
        if (card != null) {
            if (droppedUniques.remove(cardType) != null) {
                other.setUnique(cardType, card);
            }
            other.add(cardType, true);
        }

        return card;
    }

    public boolean isUniqueAlreadyDropped(CardType<T> cardType) {
        return droppedUniques.containsKey(cardType);
    }

    public void setUnique(CardType<T> cardType, T card) {
        droppedUniques.put(cardType, card);
    }

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public StashEntry<T> get(int index) {
        return entries.get(index);
    }

    @Override
    public StashEntry<T> get(CardType<T> cardType) {
        return entryByType.get(cardType);
    }

    @Override
    public int getIndex(CardType<T> cardType) {
        int size = entries.size();
        for (int i = 0; i < size; ++i) {
            if (entries.get(i).cardType == cardType) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean contains(CardType<T> cardType) {
        return entryByType.containsKey(cardType);
    }

    @Override
    public void hash(Hash hash) {
        for (StashEntry<T> entry : entries) {
            hash.add(entry);
        }
    }

    @Override
    public int getLastViewedIndex() {
        return lastViewedIndex;
    }

    @Override
    public void setLastViewedIndex(int lastViewedIndex) {
        this.lastViewedIndex = lastViewedIndex;
    }

    public CardType<T> getRandomDrop(Rarity rarity, RandomPlugin randomPlugin) {
        CardType<T>[] possibleDrops = getPossibleDrops(rarity);
        if (possibleDrops.length <= 0) {
            return null;
        }

        int dropIndex = randomPlugin.getInt(0, possibleDrops.length - 1);
        return possibleDrops[dropIndex];
    }

    CardType<T>[] getPossibleDrops(Rarity rarity) {
        return cardByDropRarity.get(rarity);
    }

    private CardType<T>[] getPossibleDrops(StashLookup lookup, Rarity rarity) {
        return getCardByDropRarity(lookup).get(rarity);
    }

    private EnumMap<Rarity, CardType<T>[]> getCardByDropRarity(StashLookup lookup) {
        switch (lookup) {
            case Dropable:
                return cardByDropRarity;
            case DropableIncludingEldritchCards:
                return cardByDropRarityIncludingEldritchCards;
            case DropableExcludingSupporterCards:
                return cardByDropRarityExcludingSupporterCards;
        }

        throw new IllegalArgumentException("Unsupported stash lookup " + lookup);
    }

    public void addPossibleDropsExcludingSupporterCards(Wizard wizard, Rarity rarity, Collection<CardType<T>> result) {
        CardType<T>[] possibleDrops = cardByDropRarityExcludingSupporterCards.get(rarity);

        for (CardType<T> possibleDrop : possibleDrops) {
            if (isUniqueAlreadyDropped(possibleDrop)) {
                continue;
            }
            if (rarity == Rarity.Legendary && !wizard.ownsFoilCard(possibleDrop)) {
                continue;
            }

            result.add(possibleDrop);
        }
    }

    public CardType<T> getRandomDrop(Rarity rarity, RandomPlugin randomPlugin, int maxItemLevel, StashLookup lookup) {
        CardType<T>[] possibleDrops = getPossibleDrops(lookup, rarity);
        if (possibleDrops.length <= 0) {
            return null;
        }

        int amount = 0;
        for (CardType<T> possibleDrop : possibleDrops) {
            if (possibleDrop.instance().getItemLevel() <= maxItemLevel) {
                ++amount;
            } else {
                break;
            }
        }

        if (amount == 0) {
            return null;
        }

        int dropIndex = randomPlugin.getInt(0, amount - 1);
        return possibleDrops[dropIndex];
    }

    private StashEntry<T> createEntry(CardType<T> cardType) {
        T card = cardType.instance();
        if (card.isUniqueDrop()) {
            if (!droppedUniques.containsKey(cardType)) {
                setUnique(cardType, cardType.create());
            }
            if (card.isUniqueInstance()) {
                return new StashEntry<>(cardType, droppedUniques.get(cardType));
            } else {
                return new StashEntry<>(cardType);
            }
        } else {
            return new StashEntry<>(cardType);
        }
    }

    private T createCard(StashEntry<T> entry) {
        if (entry.card == entry.cardType.instance()) {
            return entry.cardType.create();
        } else {
            return entry.card;
        }
    }

    @SuppressWarnings("unchecked")
    public List<T> getAutoTransmutes() {
        List<T> result = new ArrayList<>();
        for (Object autoTransmute : autoTransmutes.keySet()) {
            result.add(((CardType<T>) autoTransmute).instance());

        }
        return result;
    }

    protected abstract CardType<T>[] getPossibleDrops();

    @Override
    public int getTransmutedCommons() {
        return transmutedCommons;
    }

    @Override
    public int getTransmutedUncommons() {
        return transmutedUncommons;
    }

    @Override
    public int getTransmutedRares() {
        return transmutedRares;
    }

    @Override
    public OnCardAdded onCardAdded() {
        return onCardAdded;
    }

    @Override
    public OnCardRemoved onCardRemoved() {
        return onCardRemoved;
    }

    public void addAutoTransmute(CardType<T> type) {
        addAutoTransmute(type, 0);
    }

    public void addAutoTransmute(CardType<T> type, int amountToKeep) {
        autoTransmutes.put(type, amountToKeep);
    }

    public void removeAutoTransmute(CardType<T> type) {
        autoTransmutes.remove(type);
    }

    public boolean isAutoTransmute(CardType<T> type) {
        return isAutoTransmute(type, false);
    }

    public boolean isAutoTransmute(CardType<T> type, boolean alreadyAdded) {
        Integer amountToKeep = autoTransmutes.get(type);
        if (amountToKeep == null) {
            return false;
        }

        if (amountToKeep <= 0) {
            return true;
        }

        if (alreadyAdded) {
            return getAmount(type) > amountToKeep;
        } else {
            return getAmount(type) >= amountToKeep;
        }
    }

    public int getAmount(CardType<T> cardType) {
        StashEntry<T> stashEntry = get(cardType);
        if (stashEntry == null) {
            return 0;
        }
        return stashEntry.amount;
    }

    private static class ItemLevelComparator implements Comparator<CardType<? extends Card>> {

        @Override
        public int compare(CardType<? extends Card> o1, CardType<? extends Card> o2) {
            int result = Integer.compare(o1.instance().getItemLevel(), o2.instance().getItemLevel());
            if (result == 0) {
                result = Integer.compare(o1.id(), o2.id());
            }
            return result;
        }
    }
}
