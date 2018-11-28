package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentLuckWithLevelBonusAbility;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

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
        return "+ " + formatPlugin.percent(bonus) + "% luck";
    }
}
