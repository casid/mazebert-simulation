package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;

public interface OnCreepHealthChangedListener {
    void onHealthChanged(Tower tower, Creep creep, double oldHealth, double newHealth);
}
