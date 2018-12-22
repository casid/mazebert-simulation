package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;

public strictfp class DungeonDoor extends Item {

    public DungeonDoor() {
        super(new DungeonDoorAbility());
    }

    @Override
    public String getName() {
        return "Dungeon Door";
    }

    @Override
    public String getDescription() {
        return "Most of the time this door is locked...";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.6";
    }

    @Override
    public String getIcon() {
        return "0074_open_door_512";
    }

    @Override
    public int getItemLevel() {
        return 50; // Can drop starting with the first horseman
    }
}
