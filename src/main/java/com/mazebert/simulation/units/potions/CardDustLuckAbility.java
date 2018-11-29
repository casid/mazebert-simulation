package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentLuckWithLevelBonusAbility;

public strictfp class CardDustLuckAbility extends PermanentLuckWithLevelBonusAbility {

    public CardDustLuckAbility() {
        super(0.2f, 0);
    }

    @Override
    public String getTitle() {
        return "Lucky Dust";
    }

    @Override
    public String getDescription() {
        return "+ " + format.percent(bonus) + "% luck";
    }
}
