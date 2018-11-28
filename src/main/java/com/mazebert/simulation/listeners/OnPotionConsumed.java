package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnPotionConsumed extends Signal<OnPotionConsumedListener> {
    public OnPotionConsumed() {
        super(OnPotionConsumedListener.class);
    }

    public void dispatch(Tower tower, Potion potion) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onPotionConsumed(tower, potion);
        }
    }
}
