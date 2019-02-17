package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.maps.MapType;

public strictfp class Map2VictoryQuest extends MapVictoryQuest {

    public Map2VictoryQuest() {
        super(MapType.ShatteredPlains);
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Wind Lord";
    }

    @Override
    public String getDescription() {
        return "Defeat all enemies in the Shattered Plains!";
    }
}
