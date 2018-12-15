package com.mazebert.simulation.stash;

import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumMap;
import java.util.EnumSet;

public strictfp class TowerStash extends Stash<Tower> {
    @SuppressWarnings("unchecked")
    public TowerStash() {
        super(new EnumMap(TowerType.class), EnumSet.noneOf(TowerType.class));
    }

    @Override
    protected TowerType[] getPossibleDrops() {
        return TowerType.values();
    }
}
