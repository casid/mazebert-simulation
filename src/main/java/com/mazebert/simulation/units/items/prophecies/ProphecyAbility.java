package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class ProphecyAbility extends Ability<Tower> {
    @Override
    public boolean isVisibleToUser() {
        return true;
    }
}
