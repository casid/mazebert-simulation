package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.maps.Map;

import java.util.UUID;

public strictfp class Game implements Hashable {
    public UUID id;
    public Map map;
    public float health = 1.0f;

    @Override
    public void hash(Hash hash) {
        hash.add(id);
    }
}
