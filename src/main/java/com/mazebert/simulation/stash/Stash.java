package com.mazebert.simulation.stash;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.CardType;

import java.util.ArrayList;
import java.util.List;

public strictfp class Stash<T extends Card> {
    private List<StashEntry<T>> entries = new ArrayList<>();
    // TODO map type => entry

    public void add(CardType<T> cardType) {
        StashEntry<T> entry = get(cardType);
        if (entry == null) {
            entries.add(new StashEntry<>(cardType));
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
            }
            return true;
        }
        return false;
    }

    private StashEntry<T> get(CardType<T> cardType) {
        for (StashEntry<T> entry : entries) {
            if (entry.cardType == cardType) {
                return entry;
            }
        }
        return null;
    }

    public int size() {
        return entries.size();
    }

    public StashEntry<T> get(int index) {
        return entries.get(index);
    }
}
