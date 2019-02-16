package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyDarknessAndNatureQuest extends RestrictedElementsQuest {
    public OnlyDarknessAndNatureQuest() {
        super(EnumSet.of(Element.Darkness, Element.Nature));
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Dark Planet";
    }
}
