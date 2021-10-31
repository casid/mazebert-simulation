package com.mazebert.simulation.units.quests;

public strictfp class NaglfarSuccessQuest extends Quest {
    public NaglfarSuccessQuest() {
        super(QuestReward.Fortune, 1);
    }

    @Override
    public String getSinceVersion() {
        return "2.5.0";
    }

    @Override
    public String getTitle() {
        return "Defeat Naglfar";
    }

    @Override
    public String getDescription() {
        return "You prevented Rag nar Rog!";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
