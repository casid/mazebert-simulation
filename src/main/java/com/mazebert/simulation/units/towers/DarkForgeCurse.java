package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.abilities.DarkItemAbility;

public strictfp class DarkForgeCurse extends Ability<Tower> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Dark Curse";
    }

    @Override
    public String getDescription() {
        return "Each time a tower equipped with a dark item gains experience, it sacrifices " + format.percent(DarkItemAbility.TRIBUTE) + "% of it in tribute to the Forge.";
    }

    @Override
    public String getIconFile() {
        return "chain_512";
    }
}
