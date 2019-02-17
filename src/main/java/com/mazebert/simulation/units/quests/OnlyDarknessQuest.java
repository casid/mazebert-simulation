package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyDarknessQuest extends RestrictedElementsQuest {
    public OnlyDarknessQuest() {
        super(EnumSet.of(Element.Darkness));
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Embrace the Dark";
    }
}
