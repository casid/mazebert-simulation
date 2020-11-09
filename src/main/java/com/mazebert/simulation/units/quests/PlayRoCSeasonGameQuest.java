package com.mazebert.simulation.units.quests;

public strictfp class PlayRoCSeasonGameQuest extends PlaySeasonGameQuest {
    @Override
    public String getSinceVersion() {
        return "2.1.0";
    }

    @Override
    public String getTitle() {
        return "Fight the insanity";
    }

    @Override
    public String getDescription() {
        return "Play a RoC season game!";
    }
}
