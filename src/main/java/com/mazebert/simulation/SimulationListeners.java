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

    public final OnEarlyCallImpossible onEarlyCallImpossible = new OnEarlyCallImpossible();
    public final OnEarlyCallPossible onEarlyCallPossible = new OnEarlyCallPossible();

    public final OnUpdate onUpdate = new OnUpdate();
    public final OnPause onPause = new OnPause();

    public final OnUnitAdded onUnitAdded = new OnUnitAdded();
    public final OnUnitRemoved onUnitRemoved = new OnUnitRemoved();

    public final OnCardDropped onCardDropped = new OnCardDropped();

    public final OnNotification onNotification = new OnNotification();

    public boolean areNotificationsEnabled() {
        return onNotification.isExposed();
    }

    public void showNotification(Unit unit, String text, int color) {
        onNotification.dispatch(unit, text, color);
    }

    public void showNotification(Unit unit, String text) {
        showNotification(unit, text, 0xffffff);
    }

    public void showExperienceNotification(Unit unit, float experience) {
        int color = experience > 0 ? 0x88ff22 : 0xaa4422;
        showNotification(unit, Sim.context().formatPlugin.experienceWithSignAndUnit(experience), color);
    }
}
