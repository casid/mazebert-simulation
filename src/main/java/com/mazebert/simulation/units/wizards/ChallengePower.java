package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class ChallengePower extends WizardTowerBuffPower {
    private static final float bonus = 0.02f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addDamageAgainstZod(bonus * getSkillLevel());
    }

    @Override
    public int getRequiredLevel() {
        return 40;
    }

    @Override
    public String getTitle() {
        return "Challenger";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " challenge damage\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0075_human_howl_512";
    }
}
