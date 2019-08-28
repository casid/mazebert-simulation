package com.mazebert.simulation.units.quests;

public strictfp class BowlPerfectGameQuest extends Quest {
    public BowlPerfectGameQuest() {
        super(QuestReward.Fortune, 1);
    }

    @Override
    public String getSinceVersion() {
        return "1.2.0";
    }

    @Override
    public String getTitle() {
        return "Perfect game";
    }

    @Override
    public String getDescription() {
        return "Good lord! You just bowled a perfect game!";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
