package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class ElderPower extends WizardTowerBuffPower {
    private static final float bonus = 0.008f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addExperienceModifier(bonus * getSkillLevel());
    }

    @Override
    public int getRequiredLevel() {
        return 40;
    }

    @Override
    public String getTitle() {
        return "Elder";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " experience bonus\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0013_flowers_512";
    }
}
