package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.GainItemOnBuildAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.Mjoelnir;

public strictfp class ThorMjoelnir extends GainItemOnBuildAbility {

    @Override
    protected Item createItem() {
        // TODO check inventory
        // TODO check other towers

        return new Mjoelnir();
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Still Worthy!";
    }

    @Override
    public String getDescription() {
        return "When built, calls " + format.card(ItemType.Mjoelnir) + " to his side and never gives it away again. Each attack casts deadly lightning from the sky. " + format.card(ItemType.Mjoelnir) + " effects are doubled.";
    }

    @Override
    public String getIconFile() {
        return "mjoelnir-512";
    }
}
