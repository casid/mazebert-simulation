package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class BabyCthulhuAbility extends Ability<Hero> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Strange Aeons";
    }

    @Override
    public String getDescription() {
        return "Maps look otherworldly.";
    }
}
