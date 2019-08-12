package com.mazebert.simulation.units.potions;

public strictfp class ResearchLight extends Research {
    public ResearchLight() {
        super(new ResearchLightAbility());
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
        return "There is a bright light shining in this elixir.";
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public boolean isLight() {
        return true;
    }
}
