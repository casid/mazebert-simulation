package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentLevelUpAbility;

public strictfp class EssenceOfWisdomAbility extends PermanentLevelUpAbility {

    public EssenceOfWisdomAbility() {
        super(10);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Drink and Learn";
    }

    @Override
    public String getDescription() {
        return "The drinker grows much wiser (no matter how dumb they started out).";
    }
}
