package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;

public interface OnTowerReplacedListener {
    void onTowerReplaced(Tower oldTower);
}
