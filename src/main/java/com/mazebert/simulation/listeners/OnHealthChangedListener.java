package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public interface OnHealthChangedListener {
    void onHealthChanged(Creep creep, double oldHealth, double newHealth);
}
