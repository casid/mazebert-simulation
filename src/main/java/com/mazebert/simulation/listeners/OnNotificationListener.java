package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public interface OnNotificationListener {
    void onNotification(Unit unit, String text, int color);
}
