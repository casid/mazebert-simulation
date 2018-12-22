package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class MagicMushroom extends Item {

    public MagicMushroom() {
        super(new MagicMushroomAbility());
    }

    @Override
    public String getName() {
        return "Magic Mushroom";
    }

    @Override
    public String getDescription() {
        return "This mushroom has the infinite potential to make you high.";
    }

    @Override
    public String getIcon() {
        return "mushroom_512";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getAuthor() {
        return "Daniel Gerhardt";
    }

    @Override
    public int getItemLevel() {
        return 16;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }
}
