package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class KeyOfWisdom extends Item {

    public KeyOfWisdom() {
        super(new KeyOfWisdomAbility());
    }

    @Override
    public String getName() {
        return "Key of Wisdom";
    }

    @Override
    public String getDescription() {
        return "Unlock the mind, invite the wisdom, become enlightened.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.5";
    }

    @Override
    public String getIcon() {
        return "0012_key_512";
    }

    @Override
    public int getItemLevel() {
        return 38;
    }

    @Override
    public String getAuthor() {
        return "Alex Nill";
    }
}
