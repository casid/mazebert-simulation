package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.towers.Tower;

public strictfp class JesterKingAbility extends HeroTowerBuffAbility {

    private static final float luckBonus = 0.4f;
    private static final float itemBonus = 0.2f;
    private static final float goldBonus = 0.5f;
    private static final float damageMalus = -0.25f;
    private static final float experienceMalus = -0.25f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addLuck(luckBonus);
        tower.addItemChance(itemBonus);
        tower.addGoldModifer(goldBonus);
        tower.addAddedRelativeBaseDamage(damageMalus);
        tower.addExperienceModifier(experienceMalus);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Loaded Dice";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(luckBonus) + " luck.\n" +
                format.percentWithSignAndUnit(itemBonus) + " item chance.\n" +
                format.percentWithSignAndUnit(goldBonus) + " gold.\n" +
                format.percentWithSignAndUnit(damageMalus) + " damage.\n" +
                format.percentWithSignAndUnit(experienceMalus) + " experience.";
    }
}
