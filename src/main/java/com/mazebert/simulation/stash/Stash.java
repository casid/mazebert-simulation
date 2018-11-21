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

    protected Stash(Map<Object, StashEntry<T>> entryByType) {
        this.entryByType = entryByType;
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
            drops.sort(ITEM_LEVEL_COMPARATOR);
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

    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public StashEntry<T> get(int index) {
        return entries.get(index);
    }

    @Override
    public void hash(Hash hash) {
        for (StashEntry<T> entry : entries) {
            hash.add(entry);
        }
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

    private StashEntry<T> get(CardType<T> cardType) {
        return entryByType.get(cardType);
    }

    private static class ItemLevelComparator implements Comparator<CardType<? extends Card>> {

        @Override
        public int compare(CardType<? extends Card> o1, CardType<? extends Card> o2) {
            return Integer.compare(o1.instance().getItemLevel(), o2.instance().getItemLevel());
        }
    }
}
