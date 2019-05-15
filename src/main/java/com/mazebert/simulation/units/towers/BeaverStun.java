package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.StunAbility;

public strictfp class BeaverStun extends StunAbility {

    public BeaverStun() {
        if (Sim.context().version >= Sim.v13) {
            setChance(0.1f);
            setChancePerLevel(0.001f);
        } else {
            setChance(0.2f);
        }
        setDuration(0.8f);
    }

    @Override
    public String getTitle() {
        return "Wood on the head";
    }

    @Override
    public String getIconFile() {
        return "wood_pieces_512";
    }
}
