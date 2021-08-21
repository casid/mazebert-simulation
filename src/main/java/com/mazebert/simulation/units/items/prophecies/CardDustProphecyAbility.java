package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.potions.PotionType;

public strictfp class CardDustProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("The next card dust you create will be " + format.card(PotionType.CardDustCrit) + ".");
    }

}
