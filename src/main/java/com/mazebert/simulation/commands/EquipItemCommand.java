package com.mazebert.simulation.commands;

import com.mazebert.simulation.units.items.ItemType;

public class EquipItemCommand extends Command {
    public ItemType itemType;
    public int towerX;
    public int towerY;
    public int inventoryIndex;
}
