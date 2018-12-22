package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnGenderChanged extends Signal<OnGenderChangedListener> {

    public void dispatch(Unit unit) {
        for (OnGenderChangedListener listener : this) {
            listener.onGenderChanged(unit);
        }
    }
}
