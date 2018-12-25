package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class RodericAbility extends HeroTowerBuffAbility {

    private static final float damageBonus = 1.0f;
    private static final float critChanceBonus = 0.25f;
    private static final int multicritBonus = 2;
    private static final float attackSpeedMalus = -0.5f;
    private static final float luckMalus = -0.5f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addAddedRelativeBaseDamage(damageBonus);
        tower.addCritChance(critChanceBonus);
        tower.addMulticrit(multicritBonus);
        tower.addAttackSpeed(attackSpeedMalus);
        tower.addLuck(luckMalus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Heavy Gear";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(damageBonus) + " damage\n" +
                format.percentWithSignAndUnit(critChanceBonus) + " crit chance\n" +
                "+" + multicritBonus + " multicrit\n" +
                format.percentWithSignAndUnit(attackSpeedMalus) + " attack speed\n" +
                format.percentWithSignAndUnit(luckMalus) + " luck";
    }
}
