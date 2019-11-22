package com.mazebert.simulation.units.quests;

public strictfp class PlayDoLSeasonGameQuest extends PlaySeasonGameQuest {
    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Warrior of light";
    }

    @Override
    public String getDescription() {
        return "Play a DoL season game!";
    }
}
