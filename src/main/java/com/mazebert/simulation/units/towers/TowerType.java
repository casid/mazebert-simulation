package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.hash.Hash;

public strictfp enum TowerType implements CardType<Tower> {

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    // THESE IDS ARE USED TO PERSIST CARDS FROM THIS STASH.
    // THEY MUST BE UNIQUE BOTH FOR SAVEGAMES AND FOR EXCHANGE WITH THE WEB.
    // 1) DO NOT ALTER EXISTING IDS!
    // 2) DO NOT REUSE DELETED IDS!
    // 3) ADD NEW IDS TO THE BOTTOM!
    Beaver(1, Beaver.class),
    Dandelion(2, Dandelion.class),
    Hitman(13, Hitman.class),
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

    public static TowerType forTower(Tower tower) {
        Class<? extends Tower> towerClass = tower.getClass();
        for (TowerType towerType : values()) {
            if (towerType.towerClass == towerClass) {
                return towerType;
            }
        }
        return null;
    }

    @Override
    public void hash(Hash hash) {
        hash.add(id);
    }
}
