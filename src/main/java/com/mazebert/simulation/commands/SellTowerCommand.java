package com.mazebert.simulation.commands;

public strictfp class SellTowerCommand extends Command {
    public int x;
    public int y;

    @Override
    public boolean isValid() {
        return true;
    }
}
