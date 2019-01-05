package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class TransmuteUniques extends TutorialItem {

    @Override
    public String getDescription() {
        String legendaryString = "<c=#ffab00>legendary</c>";
        String uniqueString = "<c=#a800ff>unique</c>";
        return "You can transmute " + uniqueString + " and " + legendaryString + " cards to magical card dust.\n\nTo do that, swipe up two unique or legendary cards, be it potions, items or towers.\n\nTry it with these two notes!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }
}
