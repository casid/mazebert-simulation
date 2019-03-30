package com.mazebert.simulation.units.potions;

public strictfp class ResearchNature extends Research {
    public ResearchNature() {
        super(new ResearchNatureAbility());
    }

    @Override
    public String getIcon() {
        return "research_nature.jpg";
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
