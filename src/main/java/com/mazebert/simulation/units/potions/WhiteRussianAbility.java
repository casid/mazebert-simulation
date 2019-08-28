package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class WhiteRussianAbility extends Ability<Tower> {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Delicious sipping";
    }

    @Override
    public String getDescription() {
        return "Level ups have the color of sweet cream.";
    }
}
