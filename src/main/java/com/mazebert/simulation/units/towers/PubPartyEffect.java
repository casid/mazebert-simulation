package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class PubPartyEffect extends Ability<Tower> {

    private final float bonus;

    public PubPartyEffect(float bonus) {
        this.bonus = bonus;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addAddedRelativeBaseDamage(bonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addAddedRelativeBaseDamage(-bonus);
        super.dispose(unit);
    }
}
