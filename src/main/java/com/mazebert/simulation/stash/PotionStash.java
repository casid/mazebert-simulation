package com.mazebert.simulation.stash;

import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;

import java.util.EnumMap;

public strictfp class PotionStash extends Stash<Potion> {
    @SuppressWarnings("unchecked")
    public PotionStash() {
        super(new EnumMap(PotionType.class));
    }

    @Override
    protected PotionType[] getPossibleDrops() {
        return PotionType.values();
    }
}