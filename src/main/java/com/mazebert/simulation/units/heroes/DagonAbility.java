package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class DagonAbility extends HeroTowerBuffAbility {

    public static final float itemChance = 0.2f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addItemChance(2 * itemChance);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The Little Difference.";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(itemChance) + " item chance.\n" +
                "Effects of eldritch cards are doubled.";
    }
}
