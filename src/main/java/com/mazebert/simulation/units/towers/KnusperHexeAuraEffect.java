package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.StackableByOriginAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class KnusperHexeAuraEffect extends StackableByOriginAbility<Creep> {

    private int totalReduction;

    @Override
    protected void dispose(Creep unit) {
        unit.addArmor(totalReduction);
        super.dispose(unit);
    }

    public void setReduction(int baseReduction) {
        totalReduction += baseReduction;
        getUnit().addArmor(-baseReduction);
    }
}
