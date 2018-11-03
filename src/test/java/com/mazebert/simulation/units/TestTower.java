package com.mazebert.simulation.units;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.towers.Tower;

import java.util.UUID;

public class TestTower extends Tower {

    public TestTower() {
        setCardId(UUID.randomUUID());
    }

    @Override
    public String getName() {
        return "Test Tower";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }
}
