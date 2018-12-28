package com.mazebert.simulation;

public strictfp enum CardCategory {
    Tower(1),
    Potion(2),
    Item(3),
    Hero(4);

    public final int id;

    CardCategory(int id) {
        this.id = id;
    }

    public static CardCategory forId(int id) {
        for (CardCategory value : values()) {
            if (value.id == id) {
                return value;
            }
        }

        return null;
    }
}
