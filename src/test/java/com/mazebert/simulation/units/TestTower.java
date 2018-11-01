package com.mazebert.simulation.units;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;

public class TestTower extends Tower {
    @Override
    public String getName() {
        return "Test Tower";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }
}
