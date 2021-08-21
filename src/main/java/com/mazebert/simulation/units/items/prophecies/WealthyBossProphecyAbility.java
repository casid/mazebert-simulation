package com.mazebert.simulation.units.items.prophecies;

public strictfp class WealthyBossProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("You will encounter a Boss that drops one additional card but no gold.");
    }

}
