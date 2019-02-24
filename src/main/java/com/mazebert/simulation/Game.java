package com.mazebert.simulation;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.maps.Map;

import java.util.UUID;

public strictfp class Game implements Hashable {
    public UUID id;
    public Map map;
    public float health = 1.0f;
    public boolean bonusRound;
    public int bonusRoundSeconds;

    @Override
    public void hash(Hash hash) {
        hash.add(id);
        // ignore map
        hash.add(health);
        hash.add(bonusRound);
        hash.add(bonusRoundSeconds);
    }

    public boolean isLost() {
        return health <= 0.0f;
    }
}
