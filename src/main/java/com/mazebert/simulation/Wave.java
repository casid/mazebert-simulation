package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.units.creeps.CreepType;

public strictfp class Wave implements Hashable {
    public int round;
    public WaveType type = WaveType.Normal;
    public CreepType creepType = CreepType.Orc;
    public int creepCount;
    public float minSecondsToNextCreep;
    public float maxSecondsToNextCreep;
    public float healthMultiplier = 1;


    @Override
    public void hash(Hash hash) {
        hash.add(round);
        hash.add(type);
        hash.add(creepCount);
        hash.add(minSecondsToNextCreep);
        hash.add(maxSecondsToNextCreep);
        hash.add(healthMultiplier);
    }
}
