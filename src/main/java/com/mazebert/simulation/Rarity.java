package com.mazebert.simulation;

public strictfp enum Rarity {
    Common(0xfefefe),
    Uncommon(0x1f64ff),
    Rare(0xffff00),
    Unique(0xa800ff),
    Legendary(0xffab00),
    ;

    Rarity(int color) {
        this.color = color;
        this.name = name();
        this.nameLowercase = name.toLowerCase();
    }

    public final String name;
    public final String nameLowercase;
    public final int color;
}
