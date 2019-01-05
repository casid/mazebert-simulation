package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class TransmuteStack extends TutorialItem {

    @Override
    public String getDescription() {
        return "You can transmute an entire stack of cards by double tapping.\n\nTry it with these four notes!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }
}
