package com.mazebert.simulation.commands;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class BuildTowerCommand extends Command {
    public TowerType towerType;
    public int x;
    public int y;

    public transient Runnable onError;
    public transient Runnable onComplete;
}
