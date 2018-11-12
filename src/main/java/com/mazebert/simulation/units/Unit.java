package com.mazebert.simulation.units;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.listeners.OnUpdate;

import java.util.ArrayList;
import java.util.List;

public abstract strictfp class Unit implements Hashable {

    public final OnUpdate onUpdate = new OnUpdate();

    private float x;
    private float y;

    private final List<Ability> abilities = new ArrayList<>();

    public void simulate(float dt) {
        onUpdate.dispatch(dt);
    }

    @Override
    public void hash(Hash hash) {
        hash.add(x);
        hash.add(y);
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @SuppressWarnings("unchecked")
    public void addAbility(Ability ability) {
        abilities.add(ability);
        ability.init(this);
    }

    public List<Ability> getAbilities() {
        return abilities;
    }

    public final boolean isInRange(Unit other, float range) {
        return isInRange(other.getX(), other.getY(), range);
    }

    public boolean isInRange(float x, float y, float range) {
        float dx = StrictMath.abs(this.x - x);
        float dy = StrictMath.abs(this.y - y);

        return dx <= range && dy <= range;
    }

    public abstract String getModelId();
}
