package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyMetropolisQuest extends RestrictedElementsQuest {
    public OnlyMetropolisQuest() {
        super(EnumSet.of(Element.Metropolis));
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Nature's Wrath";
    }
}
