package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class BeaconQuest extends Quest {

    public static final int requiredLevel = 300;

    public BeaconQuest() {
        super(QuestReward.Fortune, 1);
    }

    @Override
    public String getSinceVersion() {
        return "2.4.0";
    }

    @Override
    public String getTitle() {
        return "Lighthouse Keeper";
    }

    @Override
    public String getDescription() {
        return "Let " + format.card(TowerType.Beacon) + " reach level " + requiredLevel + ".";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
