package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public interface OnMissListener {
    void onMiss(Object origin, Creep target);
}
