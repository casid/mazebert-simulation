package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HelmOfHadesInvisibleAbility extends Ability<Tower> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Invisibility";
    }

    @Override
    public String getLevelBonus() {
        return "The carrier becomes invisible.";
    }
}
