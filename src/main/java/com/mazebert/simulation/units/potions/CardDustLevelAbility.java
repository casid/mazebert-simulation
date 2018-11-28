package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentLevelUpAbility;

public strictfp class CardDustLevelAbility extends PermanentLevelUpAbility {

    public CardDustLevelAbility() {
        super(1);
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
        return "+ 1 level up";
    }

    @Override
    public String getLevelBonus() {
        return null;
    }
}
