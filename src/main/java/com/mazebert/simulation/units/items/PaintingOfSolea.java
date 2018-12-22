package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class PaintingOfSolea extends Item {

    public PaintingOfSolea() {
        super(new PaintingOfSoleaAbility());
    }

    @Override
    public String getName() {
        return "Painting of Solea";
    }

    @Override
    public String getDescription() {
        return "This painting shows an unnatural\npretty girl.";
    }

    @Override
    public String getIcon() {
        return "pretty_girl";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 21;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
