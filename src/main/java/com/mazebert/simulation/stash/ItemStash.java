package com.mazebert.simulation.stash;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.DarkItemAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.prophecies.Prophecy;

import java.util.EnumMap;
import java.util.EnumSet;

public strictfp class ItemStash extends Stash<Item> {

    private final EnumSet<ItemType> darkItems = EnumSet.noneOf(ItemType.class);
    private final EnumSet<ItemType> prophecyItems = EnumSet.noneOf(ItemType.class);

    @SuppressWarnings("unchecked")
    public ItemStash() {
        super(new EnumMap(ItemType.class), new EnumMap(ItemType.class), new EnumMap(ItemType.class));
        populateSpecialItems();
    }

    public EnumSet<ItemType> getDarkItems() {
        return darkItems;
    }

    public EnumSet<ItemType> getProphecyItems() {
        return prophecyItems;
    }

    private void populateSpecialItems() {
        for (ItemType itemType : getPossibleDrops()) {
            if (itemType.instance().containsAbility(DarkItemAbility.class)) {
                darkItems.add(itemType);
            }

            if (itemType.instance() instanceof Prophecy) {
                prophecyItems.add(itemType);
            }
        }
    }

    @Override
    public ItemType[] getPossibleDrops() {
        return ItemType.getValues();
    }

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.Item;
    }
}
