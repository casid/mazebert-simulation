package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class SpectralCape extends Item {

    public SpectralCape() {
        super(new SpectralCapeAbility(), new SpectralSetAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    public String getName() {
        return "Cape of the Specter";
    }

    @Override
    public String getDescription() {
        return "A cape from the void.";
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
        return "cape_512";
    }

    @Override
    public int getItemLevel() {
        return 90;
    }

    @Override
    public String getAuthor() {
        return "icen";
    }

    @Override
    public boolean isBlackMarketOffer() {
        return Sim.context().version < Sim.v20;
    }
}
