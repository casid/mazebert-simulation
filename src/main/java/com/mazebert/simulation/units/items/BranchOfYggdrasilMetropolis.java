package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class BranchOfYggdrasilMetropolis extends BranchOfYggdrasil {
    public BranchOfYggdrasilMetropolis() {
        super(Element.Metropolis);
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v20, false, 2020)
        );
    }
}
