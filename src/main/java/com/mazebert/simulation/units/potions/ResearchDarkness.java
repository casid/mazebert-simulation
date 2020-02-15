package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class ResearchDarkness extends Research {
    public ResearchDarkness() {
        super(new ResearchDarknessAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2019)
        );
    }

    @Override
    public String getIcon() {
        return "research_darkness";
    }

    @Override
    public String getName() {
        return "Elixir of Darkness";
    }

    @Override
    public String getDescription() {
        return "There is a looming darkness in this elixir.";
    }

    @Override
    public String getSinceVersion() {
        return "1.6";
    }
}
