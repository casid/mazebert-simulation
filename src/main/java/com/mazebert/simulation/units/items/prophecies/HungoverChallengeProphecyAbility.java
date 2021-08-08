package com.mazebert.simulation.units.items.prophecies;

public strictfp class HungoverChallengeProphecyAbility extends ProphecyAbility {

    @Override
    public String getTitle() {
        return format.prophecyTitle("Drunk at work");
    }

    @Override
    public String getDescription() {
        return format.prophecyDescription("The next Challenge had a bad night at the pub and will start with 25% health.");
    }

}
