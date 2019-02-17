package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.maps.MapType;

public strictfp class Map1VictoryQuest extends MapVictoryQuest {

    public Map1VictoryQuest() {
        super(MapType.BloodMoor);
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Claim the Moor";
    }

    @Override
    public String getDescription() {
        return "Defeat all enemies in the Blood Moor!";
    }
}
