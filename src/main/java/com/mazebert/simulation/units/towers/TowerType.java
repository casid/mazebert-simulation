package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardType;

public strictfp enum TowerType implements CardType<Tower> {
    Hitman(1, Hitman.class),
    Dandelion(2, Dandelion.class),
    ;

    public final int id;
    public final Class<? extends Tower> towerClass;

    TowerType(int id, Class<? extends Tower> towerClass) {
        this.id = id;
        this.towerClass = towerClass;
    }

    @Override
    public Tower create() {
        try {
            Tower tower = towerClass.newInstance();
            tower.setLevel(1);
            return tower;
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
