package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class SevenLeaguesBoots extends Item {

    public SevenLeaguesBoots() {
        super(new SevenLeaguesBootsAbility());
    }

    @Override
    public String getName() {
        return "Seven-leagues boots";
    }

    @Override
    public String getDescription() {
        return "These famous boots allow the wearing tower to take strides of seven leagues per step, resulting in great speed.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0051_dash_512";
    }

    @Override
    public int getItemLevel() {
        return 40;
    }
}
