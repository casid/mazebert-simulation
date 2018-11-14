package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public strictfp class Stash<T extends Card> {
    private final List<StashEntry<T>> entries = new ArrayList<>();
    private final Map<Object, StashEntry<T>> entryByType;

    public Stash(Map<Object, StashEntry<T>> entryByType) {
        this.entryByType = entryByType;
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

    private StashEntry<T> get(CardType<T> cardType) {
        return entryByType.get(cardType);
    }

    public int size() {
        return entries.size();
    }

    public StashEntry<T> get(int index) {
        return entries.get(index);
    }
}
