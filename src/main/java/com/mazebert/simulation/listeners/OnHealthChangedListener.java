package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public interface OnHealthChangedListener {
    void onHealthChanged(Unit unit, double oldHealth, double newHealth);
}
