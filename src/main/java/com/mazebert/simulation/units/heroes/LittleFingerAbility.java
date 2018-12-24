package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class LittleFingerAbility extends HeroTowerBuffAbility {

    public static final float itemChance = 0.2f;
    public static final float itemQuality = 0.2f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addItemChance(itemChance);
        tower.addItemQuality(itemQuality);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The little difference.";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(itemChance) + " item chance\n" +
                format.percentWithSignAndUnit(itemQuality) + " item quality";
    }
}
