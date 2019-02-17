package com.mazebert.simulation.units.quests;

public strictfp class WatchCreditsQuest extends Quest {
    public WatchCreditsQuest() {
        super(QuestReward.Small, 1);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean isSimulated() {
        return false;
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Credits Watcher";
    }

    @Override
    public String getDescription() {
        return "AWESOME! You watched the ENTIRE credits!";
    }
}
