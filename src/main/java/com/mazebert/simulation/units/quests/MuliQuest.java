package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class MuliQuest extends Quest {
    public MuliQuest() {
        super(QuestReward.Medium, 200);
    }

    @Override
    public String getSinceVersion() {
        return "1.3.0";
    }

    @Override
    public String getTitle() {
        return "Monkey Mania!";
    }

    @Override
    public String getDescription() {
        return "Let " + format.card(TowerType.Muli) + " have " + getRequiredAmount() + " hangovers.";
    }
}
