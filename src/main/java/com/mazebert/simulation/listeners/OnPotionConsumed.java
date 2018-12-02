package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnPotionConsumed extends Signal<OnPotionConsumedListener> {
    public void dispatch(Tower tower, Potion potion) {
        for (OnPotionConsumedListener listener : this) {
            listener.onPotionConsumed(tower, potion);
        }
    }
}
