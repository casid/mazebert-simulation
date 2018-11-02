package com.mazebert.simulation.maps;

public strictfp class BloodMoor extends Map {

    public BloodMoor() {
        TileType t1 = new TileType("grass-1", 92, 64).buildable();
        TileType t2 = new TileType("water-1", 80, 40).water();
        TileType t3 = new TileType("stones-1", 92, 50).path();
        TileType t4 = new TileType("stones-2", 92, 44).path();
        TileType t5 = new TileType("stones-3", 93, 48).path();
        TileType t6 = new TileType("stones-4", 92, 44).path();
        TileType t7 = new TileType("water-1", 80, 40).transparentWater(); // Special water tile for corners where transparency is needed
        TileType t8 = new TileType("water-2", 80, 40).water();
        TileType t9 = new TileType("water-3", 80, 40).water();
        TileType t10 = new TileType("grass-2", 92, 64).buildable();
        TileType t11 = new TileType("grass-3", 92, 64).buildable();
        TileType t12 = new TileType("stones-5", 108, 48).path();
        TileType t13 = new TileType("stones-6", 94, 52).path();
        TileType t14 = new TileType("base-tile", 92, 310).base();

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
        addTile(new Tile(t14));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13).waypoint(1));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t12));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t1, 0.25f));
        addTile(new Tile(t10, 0.5f));
        addTile(new Tile(t1, 0.25f));
        addTile(new Tile(t4));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t6));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t5));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t4));
        addTile(new Tile(t1, 0.85f));
        addTile(new Tile(t10, 0.3f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t4));
        addTile(new Tile(t11, 0.5f));
        addTile(new Tile(t6));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t12));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13));
        addTile(new Tile(t3));
        addTile(new Tile(t5));
        addTile(new Tile(t1, 0.75f));
        addTile(new Tile(t10, 1.25f));
        addTile(new Tile(t1, 1.25f));
        addTile(new Tile(t10, 0.75f));
        addTile(new Tile(t4));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t10, 0.25f));
        addTile(new Tile(t1, 0.5f));
        addTile(new Tile(t11, 0.5f));
        addTile(new Tile(t1, 0.25f));
        addTile(new Tile(t4));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t6));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t5));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t4));
        addTile(new Tile(t1, 0.75f));
        addTile(new Tile(t1, 0.50f));
        addTile(new Tile(t11, 0.25f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t3));
        addTile(new Tile(t12));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t4));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t13));
        addTile(new Tile(t3));
        addTile(new Tile(t12));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t4).waypoint(0));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t8, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));

        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t9, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t2, -0.4f, -100));
        addTile(new Tile(t7, -0.4f));
        addTile(new Tile(t7, -0.4f));
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
}