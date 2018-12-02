package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnItemEquipped extends Signal<OnItemEquippedListener> {
   public void dispatch(Tower tower, int index, Item oldItem, Item newItem) {
       for (OnItemEquippedListener listener : this) {
           listener.onItemEquipped(tower, index, oldItem, newItem);
       }
    }
}
