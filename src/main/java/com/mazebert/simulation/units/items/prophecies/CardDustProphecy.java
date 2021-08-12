package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.Rarity;

public strictfp class CardDustProphecy extends Prophecy {

    public CardDustProphecy() {
        super(new CardDustProphecyAbility());
    }

    @Override
    public String getName() {
        return "Tale of Dust";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 74;
    }

    @Override
    public String getAuthor() {
        return "Sh1ob";
    }
}
