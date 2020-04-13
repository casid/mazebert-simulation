package com.mazebert.simulation.util;

import com.mazebert.simulation.Card;

import java.util.Comparator;

public class CardComparator implements Comparator<Card> {

    @SuppressWarnings("unused") // by ladder and client
    public static final CardComparator INSTANCE = new CardComparator();

    @Override
    public int compare(Card o1, Card o2) {
        int result = Integer.compare(o1.getRarity().ordinal(), o2.getRarity().ordinal());
        if (result != 0) {
            return result;
        }

        return compareVersions(o1.getSinceVersion(), o2.getSinceVersion());
    }

    private int compareVersions(String a, String b) {
        double va = Double.parseDouble(a);
        double vb = Double.parseDouble(b);

        return Double.compare(va, vb);
    }
}
