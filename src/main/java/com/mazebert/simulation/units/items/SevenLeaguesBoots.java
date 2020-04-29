package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class SevenLeaguesBoots extends Item {

    public SevenLeaguesBoots() {
        super(new SevenLeaguesBootsAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Seven-leagues Boots";
    }

    @Override
    public String getDescription() {
        return "These famous boots allow the carrier to stride seven leagues per step.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getIcon() {
        return "0051_dash_512";
    }

    @Override
    public int getItemLevel() {
        return 40;
    }
}
