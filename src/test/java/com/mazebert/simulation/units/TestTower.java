package com.mazebert.simulation.units;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.units.towers.CustomTowerBonus;
import com.mazebert.simulation.units.towers.Tower;

public class TestTower extends Tower {

    public TestTower() {
        setBaseCooldown(1.0f);
        setAttackType(AttackType.All);
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
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public Changelog getChangelog() {
        return null;
    }

    @Override
    public String getModelId() {
        return null;
    }

    @Override
    protected float getGoldCostFactor() {
        return 1;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
    }
}
