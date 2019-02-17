package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.maps.MapType;

public strictfp class Map3VictoryQuest extends MapVictoryQuest {

    public Map3VictoryQuest() {
        super(MapType.TwistedPaths);
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Twisted Mind";
    }

    @Override
    public String getDescription() {
        return "Defeat all enemies in the Twisted Paths!";
    }
}
