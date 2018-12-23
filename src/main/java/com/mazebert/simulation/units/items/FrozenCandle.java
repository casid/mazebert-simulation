package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class FrozenCandle extends Item {

    public FrozenCandle() {
        super(new FrozenCandleAbility(), new FrozenSetAbility());
    }

    @Override
    public String getName() {
        return "Frozen Candle";
    }

    @Override
    public String getDescription() {
        return "This candle hasn't been shining for a thousand years.";
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
        return "frozen_candle_512";
    }

    @Override
    public int getItemLevel() {
        return 55;
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
