package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnNotification extends Signal<OnNotificationListener> {

    public void dispatch(Unit unit, String text, int color) {
        for (OnNotificationListener listener : this) {
            listener.onNotification(unit, text, color);
        }
    }
}
