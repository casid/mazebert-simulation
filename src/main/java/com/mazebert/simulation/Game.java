package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;

import java.util.UUID;

public strictfp class Game implements Hashable {
    public UUID id;

    @Override
    public void hash(Hash hash) {
        hash.add(id);
    }
}
