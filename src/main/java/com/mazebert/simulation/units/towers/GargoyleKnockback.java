package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.KnockbackAbility;

public strictfp class GargoyleKnockback extends KnockbackAbility {
    public GargoyleKnockback() {
        setChance(0.07f);
        setChancePerLevel(0.0007f);
        setDistance(1.0f);
    }

    @Override
    public String getTitle() {
        return "Divine Defense";
    }

    @Override
    public String getDescription() {
        return "Hurtles from his perch to slam into the creep wave. " + super.getDescription();
    }

    @Override
    public String getIconFile() {
        return "stone1_512";
    }
}
