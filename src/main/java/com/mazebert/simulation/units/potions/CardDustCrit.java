package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class CardDustCrit extends CardDust {

    public CardDustCrit() {
        super(new CardDustCritAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }
}
