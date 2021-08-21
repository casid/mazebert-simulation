package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.potions.PotionType;

public strictfp class CardDustProphecyAbility extends ProphecyAbility {

    @Override
    public String getDescription() {
        return format.prophecyDescription("You will create card dust and it will be " + format.card(PotionType.CardDustCrit) + ".");
    }

}
