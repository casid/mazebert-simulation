package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class CardDustLuck extends CardDust {

    public CardDustLuck() {
        super(new CardDustLuckAbility());
    }

    @Override
    public String getName() {
        return "Lucky Dust";
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }
}
