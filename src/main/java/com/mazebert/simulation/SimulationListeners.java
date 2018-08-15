package com.mazebert.simulation;

import com.mazebert.simulation.listeners.*;

public class SimulationListeners {
    public final OnGameStarted onGameStarted = new OnGameStarted();
    public final OnWaveStarted onWaveStarted = new OnWaveStarted();
    public final OnUpdate onUpdate = new OnUpdate();

    public final OnUnitAdded onUnitAdded = new OnUnitAdded();
    public final OnUnitRemoved onUnitRemoved = new OnUnitRemoved();
}
