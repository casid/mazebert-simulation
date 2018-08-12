package com.mazebert.simulation;

public strictfp enum WaveType {
    Normal(10),
    Mass(20),
    Boss(1),
    Air(5),
    Challenge(1),
    MassChallenge(20),
    Horseman(1);

    public final int creepCount;

    WaveType(int creepCount) {
        this.creepCount = creepCount;
    }
}
