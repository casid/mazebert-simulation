package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class DrinkingHornAbility extends Ability<Tower> {
    private static final float potionBonus = 0.1f;
    private static final float luckBonus = 0.1f;
    private static final float missChance = 0.05f;


    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addPotionEffectiveness(potionBonus);
        unit.addLuck(luckBonus);
        unit.addChanceToMiss(missChance);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addPotionEffectiveness(-potionBonus);
        unit.addLuck(-luckBonus);
        unit.addChanceToMiss(-missChance);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Drink with the gods";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(potionBonus) + " effect of consumed potions\n" + format.percentWithSignAndUnit(luckBonus) + " luck\n" + format.percentWithSignAndUnit(missChance) + " chance to miss attacks";
    }
}
