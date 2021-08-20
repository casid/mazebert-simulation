package com.mazebert.simulation.maps;

import org.junit.jupiter.api.Test;

/**
 * Find a buildable spot on a given map, useful for unit test setups
 */
class MapTileTester {
    @Test
    void buildableTile() {
        Map map = new GoldenGrounds();

        MapGrid grid = map.getGrid();
        for (int x = 0; x < grid.getWidth(); x++) {
            for (int y = 0; y < grid.getHeight(); y++) {
                Tile tile = grid.getTile(x, y);
                if (tile != null && tile.type.buildable) {
                    System.out.println("x=" + x + ", y=" + y);
                    return;
                }
            }
        }
    }
}