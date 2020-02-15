package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class HelmOfHades extends Item {

    public HelmOfHades() {
        super(new HelmOfHadesRangeAbility(), new HelmOfHadesInvisibleAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Helm of Hades";
    }

    @Override
    public String getDescription() {
        return "This helmet was once given to Hades, ruler of the underworld.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }

    @Override
    public String getIcon() {
        return "0039_helmet_512";
    }

    @Override
    public int getItemLevel() {
        return 77;
    }
}
