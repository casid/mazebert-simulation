package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class KiwiDamage extends InstantDamageAbility {
    private final int version = Sim.context().version;

    @Override
    public boolean isOriginalDamage() {
        return version < Sim.vDoL;
    }
}
