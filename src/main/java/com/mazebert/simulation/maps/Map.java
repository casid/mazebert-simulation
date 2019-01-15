package com.mazebert.simulation.maps;

import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Path;
import com.mazebert.simulation.math.Point;

import java.util.ArrayList;
import java.util.List;

public strictfp abstract class Map {
    private final List<Tile> tiles = new ArrayList<>();
    private MapGrid grid;
    private Point startWaypoint;
    private Point endWaypoint;
    private Path groundPath;
    private Path airPath;
    private int maxScrollY;
    private int minScrollY;

    protected void addTile(Tile tile) {
        tiles.add(tile);
    }

    @SuppressWarnings("unused")
    public List<Tile> getTiles() {
        return tiles;
    }

    public MapGrid getGrid() {
        if (grid == null) {
            grid = createGrid();
        }
        return grid;
    }

    public Path getGroundPath() {
        if (groundPath == null) {
            groundPath = computePath(MapGrid.WALKABLE);
        }
        return groundPath;
    }

    public Path getAirPath() {
        if (airPath == null) {
            airPath = computePath(MapGrid.FLYABLE);
        }
        return airPath;
    }

    @SuppressWarnings("unused")
    public String getAtlas() {
        return "maps/tiles.xml";
    }

    public Point getStartWaypoint() {
        return startWaypoint;
    }

    @SuppressWarnings("unused")
    public int getMaxScrollY() {
        return maxScrollY;
    }

    public void setMaxScrollY(int maxScrollY) {
        this.maxScrollY = maxScrollY;
    }

    @SuppressWarnings("unused")
    public int getMinScrollY() {
        return minScrollY;
    }

    public void setMinScrollY(int minScrollY) {
        this.minScrollY = minScrollY;
    }

    public abstract int getColumns();

    private MapGrid createGrid() {
        // Find out how big the map will be
        int mapRows = tiles.size() / getColumns();
        int mapCols = getColumns();

        // If we want to automatically compensate the iso shifting of tiles, we need to make the maze bigger.
        mapCols += mapRows - 1;

        MapGrid grid = new MapGrid(mapCols, mapRows);

        for (int y = 0; y < mapRows; ++y) {
            for (int c = 0; c < getColumns(); ++c) {
                int x = y + c;

                Tile tile = tiles.get(c + y * getColumns());
                if (tile.waypointIndex == 0) {
                    startWaypoint = new Point(x, y);
                }
                if (tile.waypointIndex == 1) {
                    endWaypoint = new Point(x, y);
                }
                grid.setTile(x, y, tile);
            }
        }

        return grid;
    }

    private Path computePath(Predicate<Tile> predicate) {
        return getGrid().findPath(startWaypoint.x, startWaypoint.y, endWaypoint.x, endWaypoint.y, predicate);
    }
}
