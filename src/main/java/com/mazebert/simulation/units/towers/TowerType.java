package com.mazebert.simulation.units.towers;

public strictfp enum TowerType {
    Hitman(1, Hitman.class);

    public final int id;
    public final Class<? extends Tower> towerClass;

    TowerType(int id, Class<? extends Tower> towerClass) {
        this.id = id;
        this.towerClass = towerClass;
    }

    public Tower newInstance() {
        try {
            return towerClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static TowerType[] LOOKUP;

    static {
        int maxId = 0;
        for (TowerType towerType : TowerType.values()) {
            maxId = StrictMath.max(maxId, towerType.id);
        }
        LOOKUP = new TowerType[maxId + 1];
        for (TowerType towerType : TowerType.values()) {
            LOOKUP[towerType.id] = towerType;
        }
    }

    public static TowerType forId(int id) {
        return LOOKUP[id];
    }
}
