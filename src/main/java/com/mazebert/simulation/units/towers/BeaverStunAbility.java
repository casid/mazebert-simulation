package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.StunAbility;

public strictfp class BeaverStunAbility extends StunAbility {

    public BeaverStunAbility() {
        setChance(0.2f);
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
