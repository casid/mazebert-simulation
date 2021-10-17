package com.mazebert.simulation.maps;

import com.mazebert.java8.Predicate;
import com.mazebert.simulation.Game;
import com.mazebert.simulation.Path;
import com.mazebert.simulation.math.Point;

import java.util.ArrayList;
import java.util.List;

public strictfp abstract class Map {
    protected final MapType type;
    protected final List<Tile> tiles = new ArrayList<>();

    private MapGrid grid;
    private Point startWaypoint;
    private Point endWaypoint;
    private Path groundPath;
    private Path airPath;
    private int maxScrollY;
    private int minScrollY;

    public Map() {
        type = MapType.forClass(getClass());
    }

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
    public String getAtlas(Game game) {
        if (game.isWinter()) {
            return "maps/tiles-winter.xml";
        }
        if (game.isHalloween()) {
            return "maps/tiles-halloween.xml";
        }
        return "maps/tiles.xml";
    }

    public Point getStartWaypoint() {
        return startWaypoint;
    }

    public Point getEndWaypoint() {
        return endWaypoint;
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

    public MapType getType() {
        return type;
    }

    public int getId() {
        return type.id;
    }

    public int countWaterTiles(float x, float y, float range) {
        int amount = 0;

        int startX = StrictMath.round(x - range);
        int endX = StrictMath.round(x + range);
        int startY = StrictMath.round(y - range);
        int endY = StrictMath.round(y + range);

        for (int my = startY; my <= endY; ++my) {
            for (int mx = startX; mx <= endX; ++mx) {
                Tile tile = getGrid().getTile(mx, my);
                if(tile != null && tile.type.water) {
                    ++amount;
                }
            }
        }

        return amount;
    }
}
