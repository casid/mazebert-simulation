package com.mazebert.simulation.units.creeps;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.units.Unit;

public strictfp class Creep extends Unit {
    private float health = 100.0f;

    @Override
    public void hash(Hash hash) {
        super.hash(hash);
        hash.add(health);
    }

    public float getHealth() {
        return health;
    }

    public void setHealth(float health) {
        this.health = health;
    }

    public boolean isDead() {
        return health <= 0.0f;
    }

    @Override
    public boolean isInRange(float x, float y, float range) {
        return !isDead() && super.isInRange(x, y, range);
    }
}
