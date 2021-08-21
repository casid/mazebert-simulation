package com.mazebert.simulation.units.items.prophecies;

public strictfp class BuildTowerWithBloodProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("The next tower you build costs no gold. Instead, sacrifice 20% health. This sacrifice can end the game.");
    }

}
