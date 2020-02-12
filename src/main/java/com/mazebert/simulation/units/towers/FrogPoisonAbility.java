package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.PoisonAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class FrogPoisonAbility extends PoisonAbility {

    private final double damagePerLevel;

    public FrogPoisonAbility() {
        super(3.0f);

        if (Sim.context().version >= Sim.v20) {
            damagePerLevel = 0.02;
        } else {
            damagePerLevel = 0.01;
        }
    }

    @Override
    protected double calculatePoisonDamage(Creep target, double damage, int multicrits) {
        return (1.0 + damagePerLevel * getUnit().getLevel()) * damage;
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
        return "Deals 100% damage as poison damage over 3 seconds. This ability stacks.";
    }

    @Override
    public String getIconFile() {
        return "0034_poisonpotion_512";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit((float) damagePerLevel) + " poison damage per level";
    }
}
