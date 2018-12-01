package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import org.jusecase.signals.Signal;

public strictfp class OnNotification extends Signal<OnNotificationListener> {
    public OnNotification() {
        super(OnNotificationListener.class);
    }

    public void dispatch(Unit unit, String text, int color) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onNotification(unit, text, color);
        }
    }

    public void dispatch(String text) {
        for (int i = 0; i < size; ++i) {
            listeners[i].onGlobalNotification(text);
        }
    }
}
