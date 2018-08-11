package com.mazebert.simulation;

import com.mazebert.simulation.listeners.OnGameStarted;
import com.mazebert.simulation.listeners.OnUnitAdded;
import com.mazebert.simulation.listeners.OnUpdate;

public class SimulationListeners {
    public final OnGameStarted onGameStarted = new OnGameStarted();
    public final OnUpdate onUpdate = new OnUpdate();

    public final OnUnitAdded onUnitAdded = new OnUnitAdded();
}
