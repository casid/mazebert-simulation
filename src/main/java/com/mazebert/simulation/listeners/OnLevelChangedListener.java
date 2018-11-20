package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;

public interface OnLevelChangedListener {
    void onLevelChanged(Tower tower, int oldLevel, int newLevel);
}
