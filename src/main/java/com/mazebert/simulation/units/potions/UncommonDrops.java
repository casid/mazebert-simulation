package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class UncommonDrops extends Potion {
    public UncommonDrops() {
        super(new UncommonDropsAbility());
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
        return "Water of Life";
    }

    @Override
    public String getDescription() {
        return "This water is pure gold. Taste it, and it will tell you stories about long forgotten legends!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 8;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }
}
