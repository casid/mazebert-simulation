package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp class AzathothAbility extends Ability<Hero> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Chaos";
    }

    @Override
    public String getDescription() {
        return "Time has no end.\nIt's you, who will end.\nIn Madness, likely.";
    }
}
