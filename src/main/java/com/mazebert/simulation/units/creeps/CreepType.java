package com.mazebert.simulation.units.creeps;

public strictfp enum CreepType {
    Orc("orc", 58),
    Rat("rat", 58),
    Spider("spider", 58),
    Troll("troll", 58),
    Bat("bat", 58),
    AirDragon("airdragon", 58),
    Gnome("gnome", 52),
    Horseman("horsemen", 78),
    ;

    public final String modelId;
    public final int baseHeight;

    CreepType(String modelId, int baseHeight) {
        this.modelId = modelId;
        this.baseHeight = baseHeight;
    }
}
