package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.PermanentLevelUpAbility;

public strictfp class CardDustLevelAbility extends PermanentLevelUpAbility {

    public CardDustLevelAbility() {
        super(Sim.context().version > Sim.v11 ? 5 : 1);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The Essence of Wisdom";
    }
}
