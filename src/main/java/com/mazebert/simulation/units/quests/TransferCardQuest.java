package com.mazebert.simulation.units.quests;

public strictfp class TransferCardQuest extends Quest {
    public TransferCardQuest() {
        super(QuestReward.Small, 1);
    }

    @Override
    public String getSinceVersion() {
        return "2.2.0";
    }

    @Override
    public String getTitle() {
        return "Friend Support";
    }

    @Override
    public String getDescription() {
        return "Give a card to another player.";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
