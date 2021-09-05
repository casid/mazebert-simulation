package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.WaveType;

public strictfp class BossToMassProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("A " + WaveType.Boss + " wave will be transformed into a " + WaveType.Mass + " wave. It will have 20% increased health.");
    }

}
