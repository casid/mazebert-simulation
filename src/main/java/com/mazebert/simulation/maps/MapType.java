package com.mazebert.simulation.maps;

import com.mazebert.simulation.Sim;

public strictfp enum MapType {
    BloodMoor(1, BloodMoor.class, "Blood Moor"),
    ShatteredPlains(2, ShatteredPlains.class, "Shattered Plains"),
    TwistedPaths(3, TwistedPaths.class, "Twisted Paths"),
    GoldenGrounds(4, GoldenGrounds.class, "Golden Grounds"),
    DawnOfLight(5, DawnOfLight.class, "Dawn of Light"),
    ;

    private static MapType[] LOOKUP;

    private static final MapType[] STANDARD      = {BloodMoor, ShatteredPlains, TwistedPaths, GoldenGrounds};
    private static final MapType[] DAWN_OF_LIGHT = {BloodMoor, ShatteredPlains, TwistedPaths, DawnOfLight, GoldenGrounds};

    static {
        int maxId = 0;
        for (MapType mapType : MapType.values()) {
            maxId = StrictMath.max(maxId, mapType.id);
        }
        LOOKUP = new MapType[maxId + 1];
        for (MapType mapType : MapType.values()) {
            LOOKUP[mapType.id] = mapType;
        }
    }

    public final int id;
    public final String displayName;
    private final Class<? extends Map> mapClass;

    MapType(int id, Class<? extends Map> mapClass, String displayName) {
        this.id = id;
        this.displayName = displayName;
        this.mapClass = mapClass;
    }

    public static MapType[] getValues() {
        if (Sim.isDoLSeasonContent()) {
            return DAWN_OF_LIGHT;
        }
        return STANDARD;
    }

    public static MapType forId(int id) {
        return LOOKUP[id];
    }

    public static MapType forClass(Class<? extends Map> towerClass) {
        for (MapType mapType : values()) {
            if (mapType.mapClass == towerClass) {
                return mapType;
            }
        }
        return null;
    }

    public Map create() {
        try {
            return mapClass.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
