package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class ProphetLucienAbility extends HeroTowerBuffAbility {
    private static final int multiluck = 1;
    private static final float damageMalus = -0.3f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addMultiluck(1);
        tower.addAddedRelativeBaseDamage(damageMalus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Roll your Dice Twice";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(damageMalus) + " damage.\n" +
                "+" + multiluck + " multiluck.";
    }
}
