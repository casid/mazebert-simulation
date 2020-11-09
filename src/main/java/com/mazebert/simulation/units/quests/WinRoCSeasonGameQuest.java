package com.mazebert.simulation.units.quests;

public strictfp class WinRoCSeasonGameQuest extends WinSeasonGameQuest {
    @Override
    public String getSinceVersion() {
        return "2.1.0";
    }

    @Override
    public String getTitle() {
        return "Escaped the Abyss";
    }

    @Override
    public String getDescription() {
        return "Win a RoC season game!";
    }
}
