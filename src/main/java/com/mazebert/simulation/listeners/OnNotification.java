package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class OnNotification extends ExposedSignal<OnNotificationListener> {

    public void dispatch(Unit unit, String text, int color) {
        dispatchAll(l -> l.onNotification(unit, text, color));
    }

    public void dispatch(Wizard wizard, Unit unit, String text, int color) {
        dispatchAll(l -> l.onNotification(wizard, unit, text, color));
    }

    public void dispatch(String sound, String group, float volume) {
        dispatchAll(l -> l.onSound(sound, group, volume));
    }

    public void dispatch(Unit unit, String text, float fadeOutTime) {
        dispatchAll(l -> l.onNotification(unit, text, fadeOutTime));
    }
}
