package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class LycaonAbility extends HeroTowerBuffAbility {

    private static final float critChance = 0.1f;
    private static final float critDamage = 0.5f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addCritChance(critChance);
        tower.addCritDamage(critDamage);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "All in.";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(critChance) + " crit chance.\n" +
                format.percentWithSignAndUnit(critDamage) + " crit damage.";
    }
}
