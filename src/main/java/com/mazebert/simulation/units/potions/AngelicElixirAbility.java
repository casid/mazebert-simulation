package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class AngelicElixirAbility extends Ability<Tower> {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        // TODO how do we show the graphical effect in the client?
    }

    @Override
    public String getTitle() {
        return "Divine Aura";
    }

    @Override
    public String getDescription() {
        return "Turns a tower into an\nangel of light or darkness.\n(Depending on confession)";
    }
}
