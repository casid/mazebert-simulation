package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.maps.MapType;

public strictfp class Map4VictoryUnlockReginnQuest extends MapVictoryQuest {

    public Map4VictoryUnlockReginnQuest() {
        super(MapType.GoldenGrounds, QuestReward.Fortune);
    }

    @Override
    public String getSinceVersion() {
        return "1.4.0";
    }

    @Override
    public String getTitle() {
        return "Reginn the Dvergr";
    }

    @Override
    public String getDescription() {
        return "You can now forge golden cards directly!";
    }
}
