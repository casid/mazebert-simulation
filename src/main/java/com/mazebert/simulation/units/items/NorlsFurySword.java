package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class NorlsFurySword extends Item {

    public NorlsFurySword() {
        super(new NorlsFurySwordAbility(), new NorlsFurySetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Norl's Steel";
    }

    @Override
    public String getDescription() {
        return "A long-forgotten sword once wielded by Norl, an ancient hero of the North. Some of his power still resides within the blade ...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getIcon() {
        return "0020_magicweapon_512";
    }

    @Override
    public int getItemLevel() {
        return 10;
    }
}
