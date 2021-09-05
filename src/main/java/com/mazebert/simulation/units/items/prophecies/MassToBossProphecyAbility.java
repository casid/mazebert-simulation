package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.WaveType;

public strictfp class MassToBossProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("A " + WaveType.Mass + " wave will be transformed into a " + WaveType.Boss + " wave. It will have 20% increased health.");
    }

}
