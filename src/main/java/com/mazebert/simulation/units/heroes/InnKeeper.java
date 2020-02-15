package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class InnKeeper extends Hero {

    public InnKeeper() {
        addAbility(new InnKeeperAbility());
        addAbility(new ShadowManeAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2014)
        );
    }

    @Override
    public String getName() {
        return "Innkeeper";
    }

    @Override
    public String getDescription() {
        return "Welcome traveller!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public String getIcon() {
        return "innkeeper_512";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getAuthor() {
        return "Vigi";
    }

    @Override
    public boolean isForgeable() {
        return false;
    }

    @Override
    public boolean isSupporterReward() {
        return true;
    }
}
