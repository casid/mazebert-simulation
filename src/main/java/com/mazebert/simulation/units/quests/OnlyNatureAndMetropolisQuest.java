package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyNatureAndMetropolisQuest extends RestrictedElementsQuest {
    public OnlyNatureAndMetropolisQuest() {
        super(EnumSet.of(Element.Nature, Element.Metropolis));
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Green City";
    }
}
