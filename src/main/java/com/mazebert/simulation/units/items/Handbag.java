package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Handbag extends Item {

    public Handbag() {
        super(new HandbagAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Handbag";
    }

    @Override
    public String getDescription() {
        return "This handbag is the latest fashion ... and quite expensive.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0011_leather_512";
    }

    @Override
    public int getItemLevel() {
        return 8;
    }
}
