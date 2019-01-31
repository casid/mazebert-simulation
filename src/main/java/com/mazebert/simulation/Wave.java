package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.units.creeps.CreepModifier;
import com.mazebert.simulation.units.creeps.CreepType;

public strictfp class Wave implements Hashable {
    public int round;
    public WaveOrigin origin = WaveOrigin.Game;
    public WaveType type = WaveType.Normal;
    public CreepType creepType = CreepType.Orc;
    public ArmorType armorType = ArmorType.Ber;
    public CreepModifier creepModifier1;
    public CreepModifier creepModifier2;
    public int creepCount;
    public transient int remainingCreepCount;
    public float minSecondsToNextCreep;
    public float maxSecondsToNextCreep;


    @SuppressWarnings("Duplicates")
    @Override
    public void hash(Hash hash) {
        hash.add(round);
        hash.add(type);
        hash.add(creepType);
        hash.add(armorType);
        hash.add(creepModifier1);
        hash.add(creepModifier2);
        hash.add(creepCount);
        // ignore remainingCreepCount
        hash.add(minSecondsToNextCreep);
        hash.add(maxSecondsToNextCreep);
    }
}
