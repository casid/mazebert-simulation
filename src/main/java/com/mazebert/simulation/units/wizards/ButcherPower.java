package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class ButcherPower extends WizardTowerBuffPower {
    private static final float bonus = 0.02f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addCritDamage(bonus * getSkillLevel());
    }

    @Override
    public String getTitle() {
        return "Butcher";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " crit damage\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "0065_claws_512";
    }
}
