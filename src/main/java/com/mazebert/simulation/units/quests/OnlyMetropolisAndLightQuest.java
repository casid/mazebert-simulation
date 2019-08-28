package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyMetropolisAndLightQuest extends RestrictedElementsQuest {
    public OnlyMetropolisAndLightQuest() {
        super(EnumSet.of(Element.Metropolis, Element.Light));
    }

    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Don't be evil";
    }
}
