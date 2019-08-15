package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.maps.MapType;

public strictfp class Map5VictoryQuest extends MapVictoryQuest {

    public Map5VictoryQuest() {
        super(MapType.DawnOfLight);
    }

    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Blessed by light";
    }

    @Override
    public String getDescription() {
        return "Beat the Dawn of Light map!";
    }
}
