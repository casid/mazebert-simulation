package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class FrozenWater extends Item {

    public FrozenWater() {
        super(new FrozenWaterAbility(), new FrozenSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Frozen Water";
    }

    @Override
    public String getDescription() {
        return "This water has been frozen for ages.";
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
        return "frozen_water_512";
    }

    @Override
    public int getItemLevel() {
        return 13;
    }

    @Override
    public String getAuthor() {
        return "Vasuhn";
    }
}
