package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class DungeonDoor extends Item {

    public static Ability createAbility() {
        if (Sim.context().version < Sim.v13) {
            return new DungeonDoorCooldownAbility();
        }
        return new DungeonDoorAbility();
    }

    public DungeonDoor() {
        super(createAbility());
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
