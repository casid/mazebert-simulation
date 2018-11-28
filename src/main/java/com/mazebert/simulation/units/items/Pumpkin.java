package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class Pumpkin extends Item {

    public Pumpkin() {
        super(new PumpkinAbility());
    }

    @Override
    public String getName() {
        return "Pumpkin";
    }

    @Override
    public String getDescription() {
        return "You should know better than give a pumpkin to your tower.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.6";
    }

    @Override
    public String getIcon() {
        return "0106_pumpkin_512";
    }

    @Override
    public int getItemLevel() {
        return 8; // it's advanced stuff...
    }

    @Override
    public String getAuthor() {
        return "Thomadin";
    }
}
