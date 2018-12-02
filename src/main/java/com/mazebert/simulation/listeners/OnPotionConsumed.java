package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class OnPotionConsumed extends ExposedSignal<OnPotionConsumedListener> {
    public void dispatch(Tower tower, Potion potion) {
        for (OnPotionConsumedListener listener : this) {
            listener.onPotionConsumed(tower, potion);
        }

        if (isExposed()) {
            dispatchExposed(l -> l.onPotionConsumed(tower, potion));
        }
    }
}
