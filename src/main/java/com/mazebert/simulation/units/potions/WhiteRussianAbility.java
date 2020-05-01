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
        return "Delicious Sips";
    }

    @Override
    public String getDescription() {
        return "The drinker's level-ups will become sweet cream colored.";
    }
}
