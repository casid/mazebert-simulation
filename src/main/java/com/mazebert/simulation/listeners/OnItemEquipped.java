package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnItemEquipped extends Signal<OnItemEquippedListener> {
    public OnItemEquipped() {
        super(OnItemEquippedListener.class);
    }

    public void dispatch(Tower tower, int index, Item oldItem, Item newItem) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onItemEquipped(tower, index, oldItem, newItem);
        }
    }
}
