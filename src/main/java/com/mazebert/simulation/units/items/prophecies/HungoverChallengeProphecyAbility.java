package com.mazebert.simulation.units.items.prophecies;

public strictfp class HungoverChallengeProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("You will encounter a Challenge that had a bad night at the pub. The Challenge starts with 50% less health, but will damage you, if it leaks.");
    }

}
