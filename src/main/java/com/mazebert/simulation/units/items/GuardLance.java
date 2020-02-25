package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class GuardLance extends Item {

    public GuardLance() {
        super(new GuardLanceAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v20, false, 2020, "Base damage increased from 4 to 6."),
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getIcon() {
        return "guard_lance";
    }

    @Override
    public String getName() {
        return "Guard Lance";
    }

    @Override
    public String getDescription() {
        return "Great to lean against.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 21;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }
}
