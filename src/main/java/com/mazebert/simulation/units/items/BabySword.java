package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class BabySword extends Item {

    public BabySword() {
        super(new BabySwordAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Baby Sword";
    }

    @Override
    public String getDescription() {
        return "Alright, it's not the largest sword but size doesn't really matter, huh?";
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
        return "0020_magicweapon_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
