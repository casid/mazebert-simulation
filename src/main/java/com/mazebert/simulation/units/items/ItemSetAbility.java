package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnItemEquippedListener;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Set;

public abstract strictfp class ItemSetAbility extends StackableAbility<Tower> implements OnItemEquippedListener {
    private final EnumSet<ItemType> allItems;
    private final EnumMap<ItemType, Integer> currentItems = new EnumMap<>(ItemType.class);

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

    @SuppressWarnings("Java8MapApi") // Must run on non Java 8 android devices, too.
    @Override
    public void onItemEquipped(Tower tower, int index, Item oldItem, Item newItem) {
        int oldAmount = currentItems.size();

        if (Sim.context().version >= Sim.vDoL) {
            if (oldItem != null) {
                ItemType oldItemType = oldItem.getType();
                if (allItems.contains(oldItemType)) {
                    Integer amount = currentItems.get(oldItemType);
                    if (amount != null) {
                        if (amount <= 1) {
                            currentItems.remove(oldItemType);
                        } else {
                            currentItems.put(oldItemType, amount - 1);
                        }
                    }
                }
            }

            if (newItem != null) {
                ItemType newItemType = newItem.getType();
                if (allItems.contains(newItemType)) {
                    Integer amount = currentItems.get(newItemType);
                    if (amount == null) {
                        currentItems.put(newItemType, 1);
                    } else {
                        currentItems.put(newItemType, amount + 1);
                    }
                }
            }

        } else if (Sim.context().version >= Sim.v13) {
            if (oldItem != null) {
                ItemType oldItemType = oldItem.getType();
                if (allItems.contains(oldItemType)) {
                    currentItems.remove(oldItemType);
                }
            }

            if (newItem != null) {
                ItemType newItemType = newItem.getType();
                if (allItems.contains(newItemType)) {
                    currentItems.put(newItemType, 1);
                }
            }
        } else {
            if (newItem != null) {
                ItemType newItemType = newItem.getType();
                if (allItems.contains(newItemType)) {
                    currentItems.put(newItemType, 1);
                }
            }

            if (oldItem != null) {
                ItemType oldItemType = oldItem.getType();
                if (allItems.contains(oldItemType)) {
                    currentItems.remove(oldItemType);
                }
            }
        }

        int newAmount = currentItems.size();
        if (oldAmount != newAmount) {
            updateSetBonus(currentItems.keySet(), oldAmount, newAmount);
        }
    }

    protected abstract void updateSetBonus(Set<ItemType> items, int oldAmount, int newAmount);

    @Override
    public boolean isVisibleToUser() {
        return true;
    }
}
