package com.mazebert.simulation.units.items.prophecies;

public strictfp class WealthyBossProphecyAbility extends ProphecyAbility {

    @Override
    public String getTitle() {
        return format.prophecyTitle("Easy come, easy go");
    }

    @Override
    public String getDescription() {
        return format.prophecyDescription("The next Boss drops 2 additional cards but no gold.");
    }

}
