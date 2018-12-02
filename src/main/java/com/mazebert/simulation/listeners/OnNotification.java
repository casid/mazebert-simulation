package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public strictfp class OnNotification extends ExposedSignal<OnNotificationListener> {

    public void dispatch(Unit unit, String text, int color) {
        dispatchAll(l -> l.onNotification(unit, text, color));
    }
}
