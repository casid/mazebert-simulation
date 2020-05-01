package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class ResearchMetropolis extends Research {
    public ResearchMetropolis() {
        super(new ResearchMetropolisAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2019)
        );
    }

    @Override
    public String getIcon() {
        return "research_metropolis";
    }

    @Override
    public String getName() {
        return "Elixir of Metropolis";
    }

    @Override
    public String getDescription() {
        return "Technology, data, and money are buzzing inside this bottle.";
    }

    @Override
    public String getSinceVersion() {
        return "1.6";
    }
}
