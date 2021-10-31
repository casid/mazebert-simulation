package com.mazebert.simulation.units.creeps;

public strictfp enum CreepType {
    Orc("orc"),
    Rat("rat"),
    Spider("spider"),
    Troll("troll"),
    Bat("bat"),
    AirDragon("airdragon"),
    Gnome("gnome"),
    Horseman("horsemen"),
    Challenge("challenge"),
    TimeLord("timelord"),
    Skull("skull"),
    Zombie("zombie"),
    Worm("worm"),
    SwampThing("swampthing"),
    Naglfar("naglfar"),
    ;

    public final String modelId;

    CreepType(String modelId) {
        this.modelId = modelId;
    }
}
