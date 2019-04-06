package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class NathavielExperienceAbility extends HeroTowerBuffAbility {

    private static final float experience = 0.4f;
    private static final float damage = -0.3f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addExperienceModifier(experience);
        tower.addAddedRelativeBaseDamage(damage);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Orm!";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(experience) + " experience\n" +
                format.percentWithSignAndUnit(damage) + " damage";
    }
}
