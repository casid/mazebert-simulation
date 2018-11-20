package com.mazebert.simulation.stash;

import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;

import java.util.EnumMap;

public strictfp class ItemStash extends Stash<Item> {
    @SuppressWarnings("unchecked")
    public ItemStash() {
        super(new EnumMap(ItemType.class));
    }
}
