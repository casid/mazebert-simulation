package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class UnluckyPants extends Item {

    public UnluckyPants() {
        super(new UnluckyPantsAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getName() {
        return "Unlucky Pants";
    }

    @Override
    public String getDescription() {
        return "Chances to get laid are extremely low with these pants on.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.1";
    }

    @Override
    public String getIcon() {
        return "iron_pants_512";
    }

    @Override
    public int getItemLevel() {
        return 51;
    }
}
