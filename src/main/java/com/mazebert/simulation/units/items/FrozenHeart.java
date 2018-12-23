package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class FrozenHeart extends Item {

    public FrozenHeart() {
        super(new FrozenHeartAbility(), new FrozenSetAbility());
    }

    @Override
    public String getName() {
        return "Frozen Heart";
    }

    @Override
    public String getDescription() {
        return "Life still resides within this heart.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "frozen_heart_512";
    }

    @Override
    public int getItemLevel() {
        return 57;
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
