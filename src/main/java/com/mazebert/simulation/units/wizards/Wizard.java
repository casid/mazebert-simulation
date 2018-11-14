package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class Wizard extends Unit {
    public final TowerStash towerStash = new TowerStash();

    @Override
    public String getModelId() {
        return null;
    }
}
