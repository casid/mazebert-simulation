package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.units.CooldownUnit;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.listeners.OnAttack;

public strictfp class Tower extends Unit implements CooldownUnit {

    public final OnAttack onAttack = new OnAttack();

    private float cooldown = Float.MAX_VALUE;


    public float getCooldown() {
        return cooldown;
    }

    public void setCooldown(float cooldown) {
        this.cooldown = cooldown;
    }

    @Override
    public void hash(Hash hash) {
        super.hash(hash);
        hash.add(cooldown);
    }
}
