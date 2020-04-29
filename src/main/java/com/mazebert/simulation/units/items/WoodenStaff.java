package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class WoodenStaff extends Item {
    public WoodenStaff() {
        super(new WoodenStaffAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Wooden Staff";
    }

    @Override
    public String getDescription() {
        return "Generations of shepherds have used this staff to teach their sheep to knock it off.";
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
        return "0101_Wooden_staff_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }
}
