package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class PoisonArrow extends Item {

    public PoisonArrow() {
        super(new PoisonArrowAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2016)
        );
    }

    @Override
    public String getName() {
        return "Hydra Arrow";
    }

    @Override
    public String getDescription() {
        return "A hydra's poisonous blood drips from this arrow.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getSinceVersion() {
        return "1.4";
    }

    @Override
    public String getIcon() {
        return "poison_arrow";
    }

    @Override
    public int getItemLevel() {
        return 76;
    }

    @Override
    public String getAuthor() {
        return "TheMarine";
    }

    @Override
    public boolean isBlackMarketOffer() {
        return Sim.context().version < Sim.vRoC;
    }
}
