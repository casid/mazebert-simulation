package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class Trident extends Item {

    public Trident() {
        super(new TridentAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2019)
        );
    }

    @Override
    public String getName() {
        return "Trident of Poseidon";
    }

    @Override
    public String getDescription() {
        return "Crafted by the three Cyclopes.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.5";
    }

    @Override
    public String getIcon() {
        return "0056_throw_512";
    }

    @Override
    public int getItemLevel() {
        return 87;
    }

    @Override
    public boolean isBlackMarketOffer() {
        return Sim.context().version < Sim.vRoC;
    }
}
