package com.mazebert.simulation.maps;

public class TileType {
    public final String name;
    public final int pivotX;
    public final int pivotY;
    public String blendMode = "normal";
    public float alpha = 1.0f;
    public boolean walkable;
    public boolean flyable;
    public boolean buildable;

    public TileType(String name, int pivotX, int pivotY) {
        this.name = name;
        this.pivotX = pivotX;
        this.pivotY = pivotY;
    }

    public TileType buildable() {
        buildable = true;
        return this;
    }

    public TileType flyable() {
        flyable = true;
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
        return blendMode("screen").flyable();
    }

    public TileType base() {
        return flyable();
    }
}
