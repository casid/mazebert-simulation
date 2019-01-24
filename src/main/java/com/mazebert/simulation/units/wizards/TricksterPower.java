package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class TricksterPower extends WizardTowerBuffPower {
    private static final float bonus = 0.01f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addLuck(bonus * getSkillLevel());
    }

    @Override
    public int getRequiredLevel() {
        return 18;
    }

    @Override
    public String getTitle() {
        return "Trickster";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(bonus * getSkillLevel()) + " more luck\nfor all towers";
    }

    @Override
    public String getIconFile() {
        return "pearl_512";
    }
}
