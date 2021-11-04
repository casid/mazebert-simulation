package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public abstract strictfp class UnlockGodQuest extends Quest {

    public static int REQUIRED_AMOUNT = 40;

    public UnlockGodQuest() {
        super(QuestReward.Huge, REQUIRED_AMOUNT);
    }

    protected abstract TowerType getGod();

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getSinceVersion() {
        return "2.3.0";
    }

    @Override
    public String getTitle() {
        return "Sacrifice for " + format.card(getGod());
    }

    @Override
    public String getDescription() {
        return "Slay " + requiredAmount + " challenges in the name of " + format.card(getGod());
    }
}
