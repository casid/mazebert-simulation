package com.mazebert.simulation.units.items;

import com.mazebert.simulation.listeners.OnItemEquippedListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

import java.util.EnumSet;

public abstract strictfp class ItemSetAbility extends StackableAbility<Tower> implements OnItemEquippedListener {
    private final EnumSet<ItemType> allItems;
    private final EnumSet<ItemType> currentItems = EnumSet.noneOf(ItemType.class);

    public ItemSetAbility(EnumSet<ItemType> allItems) {
        this.allItems = allItems;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onItemEquipped.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onItemEquipped.remove(this);
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        // not used
    }

    protected int getAmount() {
        return currentItems.size();
    }

    @Override
    public void onItemEquipped(Tower tower, int index, Item oldItem, Item newItem) {
        int oldAmount = currentItems.size();

        if (newItem != null) {
            ItemType newItemType = newItem.getType();
            if (allItems.contains(newItemType)) {
                currentItems.add(newItemType);
            }
        }

        if (oldItem != null) {
            ItemType oldItemType = oldItem.getType();
            if (allItems.contains(oldItemType)) {
                currentItems.remove(oldItemType);
            }
        }

        int newAmount = currentItems.size();
        if (oldAmount != newAmount) {
            updateSetBonus(currentItems, oldAmount, newAmount);
        }
    }

    protected abstract void updateSetBonus(EnumSet<ItemType> items, int oldAmount, int newAmount);

    @Override
    public boolean isVisibleToUser() {
        return true;
    }
}
