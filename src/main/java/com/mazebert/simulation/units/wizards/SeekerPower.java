package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class SeekerPower extends WizardTowerBuffPower {
    private static final float bonus = 0.01f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addItemQuality(bonus * getSkillLevel());
    }

    @Override
    public int getRequiredLevel() {
        return 40;
    }

    @Override
    public String getTitle() {
        return "Seeker";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " item quality\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "hoard_512";
    }
}
