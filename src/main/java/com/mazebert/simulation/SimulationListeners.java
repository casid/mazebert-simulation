package com.mazebert.simulation;

import com.mazebert.simulation.listeners.*;
import com.mazebert.simulation.units.Unit;

public strictfp class SimulationListeners {
    public final OnGameInitialized onGameInitialized = new OnGameInitialized();
    public final OnGameCountDown onGameCountDown = new OnGameCountDown();
    public final OnGameStarted onGameStarted = new OnGameStarted();

    public final OnWaveCountDown onWaveCountDown = new OnWaveCountDown();
    public final OnWaveStarted onWaveStarted = new OnWaveStarted();
    public final OnWaveFinished onWaveFinished = new OnWaveFinished();

    public final OnUpdate onUpdate = new OnUpdate();

    public final OnUnitAdded onUnitAdded = new OnUnitAdded();
    public final OnUnitRemoved onUnitRemoved = new OnUnitRemoved();

    public final OnNotification onNotification = new OnNotification();

    public boolean areNotificationsEnabled() {
        return onNotification.size() > 0;
    }

    public void showNotification(Unit unit, String text, int color) {
        onNotification.dispatch(unit, text, color);
    }
}
