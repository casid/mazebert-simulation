package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class VikingBlodMeadRiggedCardAbility extends Ability<Tower> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return format.prophecyTitle("Rigged Card");
    }

    @Override
    public String getDescription() {
        return format.prophecyDescription("This card is in your starting hand.");
    }
}
