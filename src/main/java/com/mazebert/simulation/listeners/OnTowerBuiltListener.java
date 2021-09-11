package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;

public interface OnTowerBuiltListener {
    void onTowerBuilt(Tower oldTower);
}
