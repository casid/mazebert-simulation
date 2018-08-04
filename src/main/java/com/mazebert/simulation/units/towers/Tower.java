package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.units.CooldownUnit;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.listeners.OnAttack;

public strictfp class Tower extends Unit implements CooldownUnit {

    public final OnAttack onAttack = new OnAttack();

    private float cooldown = Float.MAX_VALUE;
    private float range = 1.0f;
    private int color;


    @Override
    public void hash(Hash hash) {
        super.hash(hash);
        hash.add(cooldown);
        hash.add(range);
        hash.add(color);
    }

    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    public float getRange() {
        return range;
    }

    public void setRange(float range) {
        this.range = range;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
}
