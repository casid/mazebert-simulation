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
    public boolean water;

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

    public TileType alpha(float alpha) {
        this.alpha = alpha;
        return this;
    }

    public TileType blendMode(String blendMode) {
        this.blendMode = blendMode;
        return this;
    }

    public TileType water() {
        water = true;
        return flyable();
    }

    public TileType path() {
        return walkable().flyable();
    }

    public TileType transparentWater() {
        return blendMode("Screen").alpha(0.8f).water();
    }

    public TileType base() {
        return flyable();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TileType tileType = (TileType) o;

        if (Float.compare(tileType.pivotX, pivotX) != 0) return false;
        if (Float.compare(tileType.pivotY, pivotY) != 0) return false;
        if (Float.compare(tileType.alpha, alpha) != 0) return false;
        if (walkable != tileType.walkable) return false;
        if (flyable != tileType.flyable) return false;
        if (buildable != tileType.buildable) return false;
        if (!name.equals(tileType.name)) return false;
        return blendMode != null ? blendMode.equals(tileType.blendMode) : tileType.blendMode == null;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + (pivotX != 0.0f ? Float.floatToIntBits(pivotX) : 0);
        result = 31 * result + (pivotY != 0.0f ? Float.floatToIntBits(pivotY) : 0);
        result = 31 * result + (blendMode != null ? blendMode.hashCode() : 0);
        result = 31 * result + (alpha != 0.0f ? Float.floatToIntBits(alpha) : 0);
        result = 31 * result + (walkable ? 1 : 0);
        result = 31 * result + (flyable ? 1 : 0);
        result = 31 * result + (buildable ? 1 : 0);
        return result;
    }
}
