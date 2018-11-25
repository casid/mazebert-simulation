package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.stash.TowerStash;
import com.mazebert.simulation.units.Currency;
import com.mazebert.simulation.units.Unit;

public strictfp class Wizard extends Unit {
    public final TowerStash towerStash = new TowerStash();
    public final ItemStash itemStash = new ItemStash();
    public long money;
    public Currency currency = Currency.Gold;

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
