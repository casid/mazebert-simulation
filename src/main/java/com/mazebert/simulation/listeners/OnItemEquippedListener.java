package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;

public interface OnItemEquippedListener {
    void onItemEquipped(Tower tower, int index, Item oldItem, Item newItem, boolean userAction);
}
