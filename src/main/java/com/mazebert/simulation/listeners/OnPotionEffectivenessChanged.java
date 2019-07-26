package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.towers.Tower;
import org.jusecase.signals.Signal;

public strictfp class OnPotionEffectivenessChanged extends Signal<OnPotionEffectivenessChangedListener> {
    public void dispatch(Tower tower) {
        for (OnPotionEffectivenessChangedListener listener : this) {
            listener.onPotionEffectivenessChanged(tower);
        }
    }
}
