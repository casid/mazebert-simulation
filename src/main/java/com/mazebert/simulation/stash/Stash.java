package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.plugins.random.RandomPlugin;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract strictfp class Stash<T extends Card> implements ReadonlyStash<T>, Hashable {
    private final List<StashEntry<T>> entries = new ArrayList<>();
    private final Map<Object, StashEntry<T>> entryByType;
    private final EnumMap<Rarity, List<CardType<T>>> cardByDropRarity = new EnumMap<>(Rarity.class);

    protected Stash(Map<Object, StashEntry<T>> entryByType) {
        this.entryByType = entryByType;

        for (Rarity rarity : Rarity.values()) {
            cardByDropRarity.put(rarity, new ArrayList<>());
        }

        for (CardType<T> possibleDrop : getPossibleDrops()) {
            T card = possibleDrop.instance();
            if (card.isDropable()) {
                cardByDropRarity.get(card.getDropRarity()).add(possibleDrop);
            }
        }
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

    public CardType<T> getRandomDrop(Rarity rarity, RandomPlugin randomPlugin) {
        List<CardType<T>> possibleDrops = cardByDropRarity.get(rarity);
        if (possibleDrops.isEmpty()) {
            return null;
        }

        int dropIndex = randomPlugin.getInt(0, possibleDrops.size());

        // TODO check if unique/legendary card has already dropped

        return possibleDrops.get(dropIndex);
    }

    protected abstract CardType<T>[] getPossibleDrops();

    private StashEntry<T> get(CardType<T> cardType) {
        return entryByType.get(cardType);
    }
}
