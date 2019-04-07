package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class VexPower extends WizardTowerBuffPower {
    private static final float bonus = 0.005f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addDamageAgainstVex(bonus * getSkillLevel());
    }

    @Override
    public int getRequiredLevel() {
        return 40;
    }

    @Override
    public String getTitle() {
        return "Penetrator of Vex";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " damage against Vex\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0075_human_howl_512";
    }
}
