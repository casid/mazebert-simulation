package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.Ability;

public strictfp abstract class BlackMarketItem extends Item {
    public BlackMarketItem(Ability... abilities) {
        super(abilities);
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
