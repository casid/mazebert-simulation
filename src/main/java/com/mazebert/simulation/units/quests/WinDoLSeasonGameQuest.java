package com.mazebert.simulation.units.quests;

public strictfp class WinDoLSeasonGameQuest extends WinSeasonGameQuest {
    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Champion of light";
    }

    @Override
    public String getDescription() {
        return "Win a DoL season game!";
    }
}
