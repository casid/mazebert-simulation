package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class TransmuteUniques extends TutorialItem {

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getDescription() {
        String legendaryString = format.rarity(Rarity.Legendary);
        String uniqueString = format.rarity(Rarity.Unique);
        return "You can transmute " + uniqueString + " and " + legendaryString + " cards into magical card dust.\n\nTo do so, swipe up on two unique or legendary cards, be it potions, items, or towers.\n\nTry it with these two notes!";
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
