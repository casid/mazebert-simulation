package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class BaluQuest extends Quest {
    public BaluQuest() {
        super(QuestReward.Medium, 200);
    }

    @Override
    public String getSinceVersion() {
        return "1.3.0";
    }

    @Override
    public String getTitle() {
        return "It's so fluffy!!!";
    }

    @Override
    public String getDescription() {
        return "Let " + format.card(TowerType.Balu) + " be cuddled " + getRequiredAmount() + " times.";
    }
}
