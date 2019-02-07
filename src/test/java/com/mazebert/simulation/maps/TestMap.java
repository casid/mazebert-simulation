package com.mazebert.simulation.maps;

public strictfp class TestMap extends Map {

    private final int size;

    public TestMap(int size) {
        this.size = size;
        TileType ground = new TileType(null, 0, 0).buildable();

        for (int x = 0; x < size; ++x) {
            for (int y = 0; y < size; ++y) {
                addTile(new Tile(ground));
            }
        }
    }

    public void givenNoTilesAreBuildable() {
        for (Tile tile : tiles) {
            tile.type.buildable = false;
        }
    }

    @Override
    public int getColumns() {
        return size;
    }
}