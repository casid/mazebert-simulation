package com.mazebert.simulation.units;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;

import java.util.UUID;

public class TestTower extends Tower {

    public TestTower() {
    }

    @Override
    public String getName() {
        return "Test Tower";
    }

    @Override
    public String getDescription() {
        return "description";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getModelId() {
        return null;
    }
}
