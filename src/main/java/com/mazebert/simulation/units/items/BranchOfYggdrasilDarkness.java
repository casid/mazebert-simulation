package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class BranchOfYggdrasilDarkness extends BranchOfYggdrasil {
    public BranchOfYggdrasilDarkness() {
        super(Element.Darkness);
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v20, false, 2020)
        );
    }
}
