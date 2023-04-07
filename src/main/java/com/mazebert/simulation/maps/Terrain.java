package com.mazebert.simulation.maps;

public strictfp enum Terrain {
    Land(0xcfff68),
    Water(0x66efff),
    ;

    public final int color;

    Terrain(int color) {
        this.color = color;
    }
}
