package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.potions.PotionType;

public strictfp class CardDustProphecyAbility extends ProphecyAbility {

    @Override
    public String getTitle() {
        return format.prophecyTitle("Fortune favors the critical");
    }

    @Override
    public String getDescription() {
        return format.prophecyDescription("You will receive " + format.card(PotionType.CardDustCrit) + " when transmuting uniques.");
    }

}
