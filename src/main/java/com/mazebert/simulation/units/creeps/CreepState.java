package com.mazebert.simulation.units.creeps;

public strictfp enum CreepState {

    // Add new entries to the bottom, since this is hashed by ordinal.
    Running,
    Hit,
    Death,
    Dead,
    Knockback
}
