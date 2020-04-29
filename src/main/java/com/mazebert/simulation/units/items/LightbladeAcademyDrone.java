package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class LightbladeAcademyDrone extends Item {

    public LightbladeAcademyDrone() {
        super(new LightbladeAcademyDroneAbility(), new LightbladeAcademySetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getName() {
        return "GT1, the Little Robot";
    }

    @Override
    public String getDescription() {
        return "I will train you to become the greatest warrior in the galaxy.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getIcon() {
        return "gt1_512";
    }

    @Override
    public int getItemLevel() {
        return 54;
    }
}
