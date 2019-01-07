package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;

public interface OnNotificationListener {
    void onNotification(Unit unit, String text, int color);
    void onNotification(Unit unit, String text, float fadeOutTime);
    void onSound(String sound, String group, float volume);
}
