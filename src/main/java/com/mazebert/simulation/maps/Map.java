package com.mazebert.simulation.maps;

import java.util.ArrayList;
import java.util.List;

public strictfp abstract class Map {
    private final List<Tile> tiles = new ArrayList<>();

    protected void addTile(Tile tile) {
        tiles.add(tile);
    }
}
