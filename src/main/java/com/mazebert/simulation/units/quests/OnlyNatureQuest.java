package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;

import java.util.EnumSet;

public strictfp class OnlyNatureQuest extends RestrictedElementsQuest {
    public OnlyNatureQuest() {
        super(EnumSet.of(Element.Nature));
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
