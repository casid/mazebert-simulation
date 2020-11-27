package com.mazebert.simulation.units.quests;

public strictfp class TransferUniqueCardQuest extends Quest {
    public TransferUniqueCardQuest() {
        super(QuestReward.Medium, 1);
    }

    @Override
    public String getSinceVersion() {
        return "2.2.0";
    }

    @Override
    public String getTitle() {
        return "Best Friend Support";
    }

    @Override
    public String getDescription() {
        return "Give a unique card to another player.";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
