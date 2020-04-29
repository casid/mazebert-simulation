package com.mazebert.simulation;

public strictfp enum ArmorType {
    Ber(0x5d8b4c),
    Fal(0x9da9bc),
    Vex(0x760983),
    Zod(0xffff00),
    ;

    public final int color;

    ArmorType(int color) {
        this.color = color;
    }
}
