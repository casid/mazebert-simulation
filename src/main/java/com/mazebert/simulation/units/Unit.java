package com.mazebert.simulation.units;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.listeners.OnUpdate;

import java.util.ArrayList;
import java.util.List;

public abstract strictfp class Unit implements Hashable {

    public final OnUpdate onUpdate = new OnUpdate();

    private float x;
    private float y;

    private List<Ability> abilities = new ArrayList<>();

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
}
