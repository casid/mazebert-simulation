package com.mazebert.simulation.maps;

public strictfp enum MapType {
    BloodMoor(1, BloodMoor.class, "Blood Moor"),
    ShatteredPlains(2, ShatteredPlains.class, "Shattered Plains"),
    TwistedPaths(2, TwistedPaths.class, "Twisted Paths"),
    ;

    private static MapType[] LOOKUP;

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
            return mapClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
