package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;

public strictfp class Wave implements Hashable {
    public int round;
    public WaveType type;
    public int creepCount;
    public float minSecondsToNextCreep;
    public float maxSecondsToNextCreep;
    public float healthMultiplier = 1;


    @Override
    public void hash(Hash hash) {
        hash.add(type);
        hash.add(creepCount);
        hash.add(minSecondsToNextCreep);
        hash.add(maxSecondsToNextCreep);
        hash.add(healthMultiplier);
    }
}
