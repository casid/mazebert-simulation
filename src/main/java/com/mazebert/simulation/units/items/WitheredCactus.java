package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class WitheredCactus extends Item {

    public WitheredCactus() {
        super(new WitheredCactusAbility(), new WitheredSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v20, false, 2020, "Increased bonus from 3% to 5%."),
                new ChangelogEntry(Sim.vDoL, false, 2019, "Reduced bonus from 15% to 3%."),
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Dried Cactus";
    }

    @Override
    public String getDescription() {
        return "The happy days of this cactus are gone.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public String getSinceVersion() {
        return "0.8";
    }

    @Override
    public String getIcon() {
        return "withered_cactus_512";
    }

    @Override
    public int getItemLevel() {
        return 49;
    }

    @Override
    public String getAuthor() {
        return "Infinity";
    }
}
