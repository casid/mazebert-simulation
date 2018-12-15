package com.mazebert.simulation.stash;

import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;

import java.util.EnumMap;
import java.util.EnumSet;

public strictfp class PotionStash extends Stash<Potion> {
    @SuppressWarnings("unchecked")
    public PotionStash() {
        super(new EnumMap(PotionType.class), EnumSet.noneOf(PotionType.class));
    }

    @Override
    protected PotionType[] getPossibleDrops() {
        return PotionType.values();
    }
}
