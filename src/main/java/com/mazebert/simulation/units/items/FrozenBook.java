package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class FrozenBook extends Item {

    public FrozenBook() {
        super(new FrozenBookAbility(), new FrozenSetAbility());
    }

    @Override
    public String getName() {
        return "Frozen Book";
    }

    @Override
    public String getDescription() {
        return "A brilliant book, but your fingers get cold as you read it.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "frozen_book_512";
    }

    @Override
    public int getItemLevel() {
        return 27;
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
