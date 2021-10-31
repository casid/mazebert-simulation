package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;

public strictfp class RagNarRogProphecy extends Prophecy {

    public RagNarRogProphecy() {
        super(new RagNarRogProphecyAbility());
    }

    @Override
    public String getName() {
        return "Rag nar Rog";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return true;
    }
}
