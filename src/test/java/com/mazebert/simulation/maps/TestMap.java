package com.mazebert.simulation.maps;

public strictfp class TestMap extends Map {

    private int size;
    private MapType mapType;

    public TestMap(int size) {
        givenSize(size);
    }

    public void givenSize(int size) {
        this.size = size;
        TileType ground = new TileType(null, 0, 0).buildable();

        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                addTile(new Tile(ground));
            }
        }
    }

    @Override
    public int getColumns() {
        return size;
    }

    public void givenNoTilesAreBuildable() {
        for (Tile tile : tiles) {
            tile.type.buildable = false;
        }
    }

    public void givenAllTilesHaveAura(MapAura aura) {
        for (Tile tile : tiles) {
            tile.aura = aura;
        }
    }

    public void givenMapType(MapType mapType) {
        this.mapType = mapType;
    }

    @Override
    public MapType getType() {
        return mapType;
    }

    public void givenAllTilesAreWalkable() {
        for (Tile tile : tiles) {
            tile.type.walkable = true;
        }
    }

    public void givenAllTilesAreWater() {
        for (Tile tile : tiles) {
            tile.type.water = true;
            tile.type.buildable = false;
        }
    }

    public void givenAllTilesAreLand() {
        for (Tile tile : tiles) {
            tile.type.water = false;
            tile.type.buildable = true;
        }
    }

    public void givenEndWaypoint(int x, int y) {
        Tile tile = tiles.get(x + y * size);
        tile.waypointIndex = 1;
    }
}