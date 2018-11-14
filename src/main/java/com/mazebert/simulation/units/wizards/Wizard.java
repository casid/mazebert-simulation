package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class Wizard extends Unit {
    public final TowerStash towerStash = new TowerStash();


    public void addTowerCard(TowerType towerType) {
        towerStash.add(towerType);
    }

    public boolean removeTowerCard(TowerType towerType) {
        return towerStash.remove(towerType);
    }

    @Override
    public String getModelId() {
        return null;
    }
}
