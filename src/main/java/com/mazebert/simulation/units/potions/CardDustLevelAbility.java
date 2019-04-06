package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentLevelUpAbility;

public strictfp class CardDustLevelAbility extends PermanentLevelUpAbility {

    public CardDustLevelAbility() {
        super(5);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Experienced Dust";
    }

    @Override
    public String getDescription() {
        return "+" + getLevels() + " level up";
    }

    @Override
    public String getLevelBonus() {
        return null;
    }
}
