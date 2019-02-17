package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.maps.MapType;

public strictfp class Map4VictoryQuest extends MapVictoryQuest {

    public Map4VictoryQuest() {
        super(MapType.GoldenGrounds, QuestReward.Fortune);
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Golden Deckmaster!";
    }

    @Override
    public String getDescription() {
        return "Defeat all enemies in the Golden Grounds!";
    }
}
