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
        return "Drink and learn.";
    }

    @Override
    public String getDescription() {
        return "Increases the wisdom of a tower, no matter how dumb the tower might be.";
    }
}
