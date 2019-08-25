package com.mazebert.simulation.commands;

import com.mazebert.simulation.units.potions.PotionType;

public strictfp class DrinkPotionCommand extends Command {
    public PotionType potionType;
    public int towerX;
    public int towerY;
    public boolean all;
}
