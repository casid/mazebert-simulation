package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class BlackWidowAuraEffect extends StackableByOriginAbility<Creep> {
    private final float dropModifier;
    private final float goldModifier;

    public BlackWidowAuraEffect(float dropModifier, float goldModifier) {
        this.dropModifier = dropModifier;
        this.goldModifier = goldModifier;
    }


    @Override
    protected void initialize(Creep unit) {
        super.initialize(unit);
        unit.setDropChance(unit.getDropChance() * dropModifier);
        unit.setGoldModifier(unit.getGoldModifier() * goldModifier);
    }

    @Override
    protected void dispose(Creep unit) {
        unit.setDropChance(unit.getDropChance() / dropModifier);
        unit.setGoldModifier(unit.getGoldModifier() / goldModifier);
        super.dispose(unit);
    }
}
