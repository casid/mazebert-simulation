package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class SchoolBook extends Item {

    public SchoolBook() {
        super(new SchoolBookAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "School Book";
    }

    @Override
    public String getDescription() {
        return "The book's cover says: \"How to Become a Successful Tower in Just 7 Days.\"";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0014_magicbook_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
