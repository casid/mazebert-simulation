package com.mazebert.simulation.units.potions;

public strictfp class ResearchDarkness extends Research {
    public ResearchDarkness() {
        super(new ResearchDarknessAbility());
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
