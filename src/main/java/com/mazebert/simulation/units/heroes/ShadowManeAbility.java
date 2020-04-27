package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class ShadowManeAbility extends HeroTowerBuffAbility {

    private static final float attackSpeed = 0.2f;
    private static final float xpBonus = 0.05f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addAttackSpeed(attackSpeed);
        tower.addExperienceModifier(xpBonus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The Meaning of Haste.";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(attackSpeed) + " attack speed.\n" +
                format.percentWithSignAndUnit(xpBonus) + " experience.";
    }
}
