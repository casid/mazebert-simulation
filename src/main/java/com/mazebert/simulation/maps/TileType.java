package com.mazebert.simulation.maps;

public strictfp class TileType {
    public final String name;
    public final float pivotX;
    public final float pivotY;
    public String blendMode;
    public float alpha = 1.0f;
    public boolean walkable;
    public boolean flyable;
    public boolean buildable;

    public TileType(String name, float pivotX, float pivotY) {
        this.name = name;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    public TileType buildable() {
        buildable = true;
        return flyable();
    }

    public TileType flyable() {
        flyable = true;
        return this;
    }

    public TileType notFlyable() {
        flyable = false;
        return this;
    }

    public TileType walkable() {
        walkable = true;
        return this;
    }

    public TileType blendMode(String blendMode) {
        this.blendMode = blendMode;
        return this;
    }

    public TileType water() {
        return flyable();
    }

    public TileType path() {
        return walkable().flyable();
    }

    public TileType transparentWater() {
        return blendMode("Screen").flyable();
    }

    public TileType base() {
        return flyable();
    }
}
