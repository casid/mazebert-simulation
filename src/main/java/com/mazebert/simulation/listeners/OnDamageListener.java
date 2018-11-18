package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public interface OnDamageListener {
    void onDamage(Object origin, Creep target, double damage);
}
