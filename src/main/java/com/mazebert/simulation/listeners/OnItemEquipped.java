package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class OnItemEquipped extends ExposedSignal<OnItemEquippedListener> {
   public void dispatch(Tower tower, int index, Item oldItem, Item newItem, boolean userAction) {
       for (OnItemEquippedListener listener : this) {
           listener.onItemEquipped(tower, index, oldItem, newItem, userAction);
       }

       if (isExposed()) {
           dispatchExposed(l -> l.onItemEquipped(tower, index, oldItem, newItem, userAction));
       }
    }
}
