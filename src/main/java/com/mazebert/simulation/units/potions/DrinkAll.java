package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class DrinkAll extends TutorialPotion {
    @Override
    public String getDescription() {
        return "You can drink an entire stack of potions. Press and hold the drink button.\n\nTry it with these drinks!";
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public boolean isTradingAllowed() {
        return false;
    }
}
