package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public interface OnLevelChangedListener {
    void onLevelChanged(Unit unit, int oldLevel, int newLevel);
}
