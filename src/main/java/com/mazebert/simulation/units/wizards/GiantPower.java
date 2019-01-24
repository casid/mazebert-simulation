package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class GiantPower extends WizardTowerBuffPower {
    private static final float bonus = 0.01f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addAddedRelativeBaseDamage(bonus * getSkillLevel());
    }

    @Override
    public String getTitle() {
        return "Giant";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " damage\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0075_human_howl_512";
    }
}
