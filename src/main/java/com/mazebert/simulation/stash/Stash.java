package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.plugins.random.RandomPlugin;

import java.util.*;

public abstract strictfp class Stash<T extends Card> implements ReadonlyStash<T>, Hashable {
    private static final ItemLevelComparator ITEM_LEVEL_COMPARATOR = new ItemLevelComparator();

    private final List<StashEntry<T>> entries = new ArrayList<>();
    private final Map<Object, StashEntry<T>> entryByType;
    private final EnumMap<Rarity, CardType<T>[]> cardByDropRarity;
    private final Set<CardType<T>> uniques;

    private transient int lastViewedIndex;

    @SuppressWarnings("unchecked")
    protected Stash(Map<Object, StashEntry<T>> entryByType, Set uniques) {
        this.entryByType = entryByType;
        this.uniques = uniques;
        this.cardByDropRarity = populateCardByDropRarity();
    }

    @SuppressWarnings("unchecked")
    private EnumMap<Rarity, CardType<T>[]> populateCardByDropRarity() {
        EnumMap<Rarity, List<CardType<T>>> temp = new EnumMap<>(Rarity.class);

        for (Rarity rarity : Rarity.values()) {
            temp.put(rarity, new ArrayList<>());
        }

        for (CardType<T> possibleDrop : getPossibleDrops()) {
            T card = possibleDrop.instance();
            if (card.isDropable()) {
                temp.get(card.getDropRarity()).add(possibleDrop);
            }
        }

        EnumMap<Rarity, CardType<T>[]> result = new EnumMap<>(Rarity.class);
        for (Rarity rarity : Rarity.values()) {
            List<CardType<T>> drops = temp.get(rarity);
            //noinspection Java8ListSort // For android 19 compatibility
            Collections.sort(drops, ITEM_LEVEL_COMPARATOR);
            result.put(rarity, drops.toArray(new CardType[0]));
        }
        return result;
    }

    public void add(CardType<T> cardType) {
        StashEntry<T> entry = get(cardType);
        if (entry == null) {
            entry = new StashEntry<>(cardType);
            entries.add(entry);
            entryByType.put(cardType, entry);
            uniques.add(cardType);
        } else {
            ++entry.amount;
        }
    }

    public boolean remove(CardType<T> cardType) {
        StashEntry<T> entry = get(cardType);
        if (entry != null) {
            --entry.amount;
            if (entry.amount <= 0) {
                entries.remove(entry);
                entryByType.remove(cardType);
            }
            return true;
        }
        return false;
    }

    public boolean isUniqueAlreadyDropped(CardType<T> cardType) {
        return uniques.contains(cardType);
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
    public void hash(Hash hash) {
        for (StashEntry<T> entry : entries) {
            hash.add(entry);
        }
    }

    @Override
    public void setLastViewedIndex(int lastViewedIndex) {
        this.lastViewedIndex = lastViewedIndex;
    }

    @Override
    public int getLastViewedIndex() {
        return lastViewedIndex;
    }

    public CardType<T> getRandomDrop(Rarity rarity, int maxItemLevel, RandomPlugin randomPlugin) {
        CardType<T>[] possibleDrops = cardByDropRarity.get(rarity);
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

        // TODO check if unique/legendary card has already dropped

        return possibleDrops[dropIndex];
    }

    protected abstract CardType<T>[] getPossibleDrops();

    private static class ItemLevelComparator implements Comparator<CardType<? extends Card>> {

        @Override
        public int compare(CardType<? extends Card> o1, CardType<? extends Card> o2) {
            return Integer.compare(o1.instance().getItemLevel(), o2.instance().getItemLevel());
        }
    }
}
