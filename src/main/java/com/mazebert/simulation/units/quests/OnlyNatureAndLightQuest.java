package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyNatureAndLightQuest extends RestrictedElementsQuest {
    public OnlyNatureAndLightQuest() {
        super(EnumSet.of(Element.Nature, Element.Light));
    }

    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Photosynthesis";
    }
}
