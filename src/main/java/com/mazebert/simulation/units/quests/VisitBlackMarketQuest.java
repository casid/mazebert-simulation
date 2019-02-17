package com.mazebert.simulation.units.quests;

public strictfp class VisitBlackMarketQuest extends Quest {

    public VisitBlackMarketQuest() {
        super(QuestReward.Small, 1);
    }

    @Override
    public boolean isSimulated() {
        return false;
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getSinceVersion() {
        return "1.1.0";
    }

    @Override
    public String getTitle() {
        return "Black Market Visitor";
    }

    @Override
    public String getDescription() {
        return "Hey! You found the black market!";
    }
}
