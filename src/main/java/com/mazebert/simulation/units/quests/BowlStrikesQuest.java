package com.mazebert.simulation.units.quests;

public strictfp class BowlStrikesQuest extends Quest {
    public BowlStrikesQuest() {
        super(QuestReward.Medium, 5);
    }

    @Override
    public String getSinceVersion() {
        return "1.2.0";
    }

    @Override
    public String getTitle() {
        return "Strike Machine";
    }

    @Override
    public String getDescription() {
        return "Play bowling and roll " + getRequiredAmount() + " strikes!";
    }
}
