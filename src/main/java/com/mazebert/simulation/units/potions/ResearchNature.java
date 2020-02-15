package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class ResearchNature extends Research {
    public ResearchNature() {
        super(new ResearchNatureAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2019)
        );
    }

    @Override
    public String getIcon() {
        return "research_nature";
    }

    @Override
    public String getName() {
        return "Elixir of Nature";
    }

    @Override
    public String getDescription() {
        return "This elixir smells like a forest full of possibilities.";
    }

    @Override
    public String getSinceVersion() {
        return "1.6";
    }
}
