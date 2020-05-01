package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentLuckWithLevelBonusAbility;

public strictfp class CardDustLuckAbility extends PermanentLuckWithLevelBonusAbility {

    public CardDustLuckAbility() {
        super(0.2f, 0);
    }

    @Override
    public String getTitle() {
        return "The Essence of Luck";
    }
}
