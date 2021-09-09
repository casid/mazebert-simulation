package com.mazebert.simulation.units.quests;

public strictfp class WinRnRSeasonGameQuest extends WinSeasonGameQuest {
    @Override
    public String getSinceVersion() {
        return "2.4.0";
    }

    @Override
    public String getTitle() {
        return "As it was foretold";
    }

    @Override
    public String getDescription() {
        return "Win an RnR season game!";
    }
}
