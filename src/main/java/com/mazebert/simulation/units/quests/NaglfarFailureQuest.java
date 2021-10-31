package com.mazebert.simulation.units.quests;

public strictfp class NaglfarFailureQuest extends Quest {
    public NaglfarFailureQuest() {
        super(QuestReward.Big, 1);
    }

    @Override
    public String getSinceVersion() {
        return "2.5.0";
    }

    @Override
    public String getTitle() {
        return "At least you tried";
    }

    @Override
    public String getDescription() {
        return "Even gods failed to destroy Naglfar.";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
