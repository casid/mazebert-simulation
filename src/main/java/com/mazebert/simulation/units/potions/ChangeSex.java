package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class ChangeSex extends Potion {
    public ChangeSex() {
        super(new ChangeSexAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRnR, false, 2021, "Removed from black market."),
                new ChangelogEntry(Sim.v10, false, 2019)
        );
    }

    @Override
    public String getIcon() {
        return "9012_SexPotion";
    }

    @Override
    public String getName() {
        return "Ila's Spirit of Metamorphosis";
    }

    @Override
    public String getDescription() {
        return "Why not explore the possibilities?";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 30;
    }

    @Override
    public String getAuthor() {
        return "mazejanovic";
    }

    @Override
    public String getSinceVersion() {
        return "1.5";
    }

    @Override
    public boolean isBlackMarketOffer() {
        return Sim.context().version < Sim.vRnR;
    }
}
