package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.hash.Hash;

public strictfp enum ItemType implements CardType<Item> {
    BabySword(1, BabySword.class);

    private static ItemType[] LOOKUP;

    static {
        int maxId = 0;
        for (ItemType itemType : ItemType.values()) {
            maxId = StrictMath.max(maxId, itemType.id);
        }
        LOOKUP = new ItemType[maxId + 1];
        for (ItemType itemType : ItemType.values()) {
            LOOKUP[itemType.id] = itemType;
        }
    }

    public final int id;
    public final Class<? extends Item> itemClass;

    ItemType(int id, Class<? extends Item> itemClass) {
        this.id = id;
        this.itemClass = itemClass;
    }

    public static ItemType forId(int id) {
        if (id < 0 || id >= LOOKUP.length) {
            return null;
        }
        return LOOKUP[id];
    }

    public static ItemType forItem(Item item) {
        Class<? extends Item> itemClass = item.getClass();
        for (ItemType itemType : values()) {
            if (itemType.itemClass == itemClass) {
                return itemType;
            }
        }
        return null;
    }

    @Override
    public Item create() {
        try {
            return itemClass.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void hash(Hash hash) {
        hash.add(id);
    }
}
