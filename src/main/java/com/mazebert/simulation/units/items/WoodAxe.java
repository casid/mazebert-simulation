package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class WoodAxe extends Item {

    public WoodAxe() {
        super(new WoodAxeAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoL, false, 2019, "Change from Well Done T-Bone Steak to Wood Axe"),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Wood Axe";
    }

    @Override
    public String getDescription() {
        return "You can cut anything with it. Wood, steaks, creeps...";
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
        return "wood_axe";
    }

    @Override
    public int getItemLevel() {
        return 11;
    }
}
