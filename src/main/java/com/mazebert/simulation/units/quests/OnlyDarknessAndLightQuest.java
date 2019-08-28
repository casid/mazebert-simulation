package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyDarknessAndLightQuest extends RestrictedElementsQuest {
    public OnlyDarknessAndLightQuest() {
        super(EnumSet.of(Element.Darkness, Element.Light));
    }

    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Gray World";
    }
}
