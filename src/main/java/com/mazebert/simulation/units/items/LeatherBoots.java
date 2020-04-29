package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class LeatherBoots extends Item {
    public LeatherBoots() {
        super(new LeatherBootsAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Leather Boots";
    }

    @Override
    public String getDescription() {
        return "The carrier runs faster, trying to escape these boots' awful odor.";
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
        return "0086_Cloth_Boots_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
