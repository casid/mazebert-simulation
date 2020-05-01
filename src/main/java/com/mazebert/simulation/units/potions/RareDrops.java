package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class RareDrops extends Potion {
    public RareDrops() {
        super(new RareDropsAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getIcon() {
        return "9004_GreedPotion";
    }

    @Override
    public String getName() {
        return "Great Water of Life";
    }

    @Override
    public String getDescription() {
        return "Whoever tastes this water hears stories of long-forgotten treasure.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 32;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
