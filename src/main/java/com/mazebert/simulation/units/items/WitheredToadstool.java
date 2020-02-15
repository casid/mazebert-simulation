package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class WitheredToadstool extends Item {

    public WitheredToadstool() {
        super(new WitheredToadstoolAbility(), new WitheredSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Rotten Toadstool";
    }

    @Override
    public String getDescription() {
        return "This toadstool tastes funny.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "withered_toadstool_512";
    }

    @Override
    public int getItemLevel() {
        return 53;
    }

    @Override
    public String getAuthor() {
        return "Infinity";
    }
}
