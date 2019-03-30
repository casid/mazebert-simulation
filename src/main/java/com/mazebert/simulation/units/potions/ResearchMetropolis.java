package com.mazebert.simulation.units.potions;

public strictfp class ResearchMetropolis extends Research {
    public ResearchMetropolis() {
        super(new ResearchMetropolisAbility());
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
        return "Technology, data and money is buzzing in this bottle.";
    }

    @Override
    public String getSinceVersion() {
        return "1.6";
    }
}
