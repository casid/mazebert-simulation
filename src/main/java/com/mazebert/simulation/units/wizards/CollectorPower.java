package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class CollectorPower extends WizardTowerBuffPower {
    private static final float bonus = 0.01f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addItemChance(bonus * getSkillLevel());
    }

    @Override
    public int getRequiredLevel() {
        return 40;
    }

    @Override
    public String getTitle() {
        return "Collector";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + "% item chance\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0032_ring_512";
    }
}
