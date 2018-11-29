package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.FrogPoisonEffect;

public strictfp class FrogPoisonAbility extends PoisonAbility {

    public FrogPoisonAbility() {
        super(FrogPoisonEffect.class, 3.0f);
    }

    @Override
    protected double calculatePoisonDamage(Creep target, double damage, int multicrits) {
        return (1.0 + 0.01 * getUnit().getLevel()) * damage;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Frog Poison";
    }

    @Override
    public String getDescription() {
        return "Whenever this tower damages a creep it deals 100% of this damage as poison damage over 3 seconds to the creep. This ability stacks.";
    }

    @Override
    public String getIconFile() {
        return "0034_poisonpotion_512";
    }

    @Override
    public String getLevelBonus() {
        return "+1% poison damage per level";
    }
}
