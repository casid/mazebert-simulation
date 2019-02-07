package com.mazebert.simulation.maps;

public strictfp class Tile {
    public final TileType type;
    public final float height;
    public final int layer;
    public int waypointIndex = -1;
    public MapAura aura;

    public Tile(TileType type) {
        this(type, 0, 0);
    }

    public Tile(TileType type, float height) {
        this(type, height, 0);
    }

    public Tile(TileType type, float height, int layer) {
        this.type = type;
        this.height = height;
        this.layer = layer;
    }

    public Tile waypoint(int index) {
        waypointIndex = index;
        return this;
    }

    public Tile aura(MapAura aura) {
        this.aura = aura;
        return this;
    }
}
