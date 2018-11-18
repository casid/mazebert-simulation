package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public interface OnDamageListener {
    void onDamage(Creep target, double damage);
}
