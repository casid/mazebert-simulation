package com.mazebert.simulation.units.quests;

public strictfp class PlayRnRSeasonGameQuest extends PlaySeasonGameQuest {
    @Override
    public String getSinceVersion() {
        return "2.4.0";
    }

    @Override
    public String getTitle() {
        return "Rag nar Rog awaits!";
    }

    @Override
    public String getDescription() {
        return "Play an RnR season game!";
    }
}
