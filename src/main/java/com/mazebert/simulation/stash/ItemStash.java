package com.mazebert.simulation.stash;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.DarkItemAbility;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;

import java.util.EnumMap;
import java.util.EnumSet;

public strictfp class ItemStash extends Stash<Item> {

    private final EnumSet<ItemType> darkItems = EnumSet.noneOf(ItemType.class);

    @SuppressWarnings("unchecked")
    public ItemStash() {
        super(new EnumMap(ItemType.class), new EnumMap(ItemType.class), EnumSet.noneOf(ItemType.class));
        populateDarkItems();
    }

    public EnumSet<ItemType> getDarkItems() {
        return darkItems;
    }

    private void populateDarkItems() {
        for (ItemType itemType : getPossibleDrops()) {
            if (itemType.instance().containsAbility(DarkItemAbility.class)) {
                darkItems.add(itemType);
            }
        }
    }

    @Override
    protected ItemType[] getPossibleDrops() {
        return ItemType.getValues();
    }

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.Item;
    }
}
