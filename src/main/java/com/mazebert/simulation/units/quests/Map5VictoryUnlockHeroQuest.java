package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.maps.MapType;

public strictfp class Map5VictoryUnlockHeroQuest extends MapVictoryQuest {

    public Map5VictoryUnlockHeroQuest() {
        super(MapType.GoldenGrounds, QuestReward.Small);
    }

    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Prophet Lucius";
    }

    @Override
    public String getDescription() {
        return "You unlocked a new hero card!";
    }
}
