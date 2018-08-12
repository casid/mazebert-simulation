package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;

public strictfp class Wave implements Hashable {
    public int creepCount;
    public float minSecondsToNextCreep;
    public float maxSecondsToNextCreep;

    @Override
    public void hash(Hash hash) {
        hash.add(creepCount);
        hash.add(minSecondsToNextCreep);
        hash.add(maxSecondsToNextCreep);
    }
}
