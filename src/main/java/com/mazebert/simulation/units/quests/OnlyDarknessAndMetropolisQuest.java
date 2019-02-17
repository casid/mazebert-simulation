package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyDarknessAndMetropolisQuest extends RestrictedElementsQuest {
    public OnlyDarknessAndMetropolisQuest() {
        super(EnumSet.of(Element.Darkness, Element.Metropolis));
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Black Mirrors";
    }
}
