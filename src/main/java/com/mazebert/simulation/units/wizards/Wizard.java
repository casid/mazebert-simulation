package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class Wizard extends Unit {
    public long money;
    public final TowerStash towerStash = new TowerStash();

    @Override
    public void hash(Hash hash) {
        super.hash(hash);

        hash.add(towerStash);
    }

    @Override
    public String getModelId() {
        return null;
    }
}
