package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;

public strictfp class ResearchLight extends Research {
    public ResearchLight() {
        super(new ResearchLightAbility());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                ChangelogEntry.DAWN_OF_LIGHT
        );
    }

    @Override
    public String getIcon() {
        return "research_light";
    }

    @Override
    public String getName() {
        return "Elixir of Light";
    }

    @Override
    public String getDescription() {
        return "A bright light shines in this elixir.";
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }
}
