package com.mazebert.simulation.stash;

import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumMap;

public strictfp class TowerStash extends Stash<Tower> {
    @SuppressWarnings("unchecked")
    public TowerStash() {
        super(new EnumMap(TowerType.class));
    }
}
