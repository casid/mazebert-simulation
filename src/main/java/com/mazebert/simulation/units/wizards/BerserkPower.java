package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class BerserkPower extends WizardTowerBuffPower {
    private static final float bonus = 0.007f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addCritChance(bonus * getSkillLevel());
    }

    @Override
    public String getTitle() {
        return "Berserk";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " crit chance\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0022_bloodyweapon_512";
    }
}
