package com.mazebert.simulation.maps;

import com.mazebert.simulation.Path;

public strictfp class DawnOfLight extends Map {

    private Path airPath;
    private Path leftGroundPath;
    private Path rightGroundPath;
    private boolean nextGroundPathLeft;

    public DawnOfLight() {
        setMaxScrollY(-340);
        setMinScrollY(-1200);

        TileType t1 = new TileType("grass-1", 92f / 180f, 64f / 152f).buildable();
        TileType t2 = new TileType("water-1", 80f / 155f, 40f / 85f).water();
        TileType t3 = new TileType("stones-1", 92f / 181f, 50f / 143f).path();
        TileType t4 = new TileType("stones-2", 92f / 175f, 44f / 136f).path();
        TileType t5 = new TileType("stones-3", 93f / 199f, 48f / 142f).path();
        TileType t6 = new TileType("stones-4", 92f / 187f, 44f / 137f).path();
        TileType t7 = new TileType("water-1", 80f / 155f, 40f / 85f).transparentWater(); // Special water tile for corners where transparency is needed
        TileType t8 = new TileType("water-2", 80f / 155f, 40f / 85f).water();
        TileType t9 = new TileType("water-3", 80f / 155f, 40f / 85f).water();
        TileType t10 = new TileType("grass-2", 92f / 180f, 64f / 152f).buildable();
        TileType t11 = new TileType("grass-3", 92f / 180f, 64f / 152f).buildable();
        TileType t12 = new TileType("stones-5", 108f / 202f, 48f / 138f).path();
        TileType t13 = new TileType("stones-6", 94f / 199f, 52f / 142f).path();
        TileType t14 = new TileType("base-tile", 92f / 180f, 310f / 396f).base();

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t6, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t6, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t14, 0.1f, 0).waypoint(1));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t10, 1.5f, 0));
        addTile(new Tile(t1, 0.7f, 0));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t11, 1.5f, 0));
        addTile(new Tile(t10, 1.0f, 0).aura(MapAura.DamageAgainstAir));
        addTile(new Tile(t11, 0.3f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t11, 0.7f, 0));
        addTile(new Tile(t11, 0.3f, 0));
        addTile(new Tile(t11, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t11, 0.8f, 0).aura(MapAura.DamageAgainstAir));
        addTile(new Tile(t10, 0.2f, 0));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t12, 0.0f, 0));
        addTile(new Tile(t10, 0.2f, 0));
        addTile(new Tile(t7, -0.2f, 0));
        addTile(new Tile(t4, 0.0f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t13, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t3, 0.0f, 0));
        addTile(new Tile(t5, 0.0f, 0).waypoint(0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t7, -0.4f, 0));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
    }

    @Override
    public int getColumns() {
        return 11;
    }

    @Override
    public Path getGroundPath() {
        nextGroundPathLeft = !nextGroundPathLeft;

        if (nextGroundPathLeft) {
            return getLeftGroundPath();
        } else {
            return getRightGroundPath();
        }
    }

    @Override
    public Path getAirPath() {
        if (airPath == null) {
            airPath = new Path(24, 19, 24, 17, 20, 17, 20, 13, 16, 13, 16, 9, 13, 8);
        }
        return airPath;
    }

    private Path getLeftGroundPath() {
        if (leftGroundPath == null) {
            leftGroundPath = new Path(22, 17, 19, 17, 19, 16, 18, 16, 18, 9, 17, 9, 17, 8, 16, 8, 16, 7, 15, 7, 15, 6, 13, 6, 13, 7);
        }
        return leftGroundPath;
    }

    private Path getRightGroundPath() {
        if (rightGroundPath == null) {
            rightGroundPath = new Path(22, 17, 22, 14, 21, 14, 21, 13, 14, 13, 14, 12, 13, 12, 13, 11, 12, 11, 12, 10, 11, 10, 11, 8, 12, 8);
        }
        return rightGroundPath;
    }
}
