package com.mazebert.simulation.units.items.prophecies;

public strictfp class BuildTowerWithBloodProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("You will build a tower that costs no gold. You will sacrifice 20% health instead.");
    }

}
