package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class NinjaPower extends WizardTowerBuffPower {
    private static final float bonus = 0.015f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addAttackSpeed(bonus * getSkillLevel());
    }

    @Override
    public String getTitle() {
        return "Ninja";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " attack speed\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0051_dash_512";
    }
}
