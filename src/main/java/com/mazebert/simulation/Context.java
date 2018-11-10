package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.plugins.random.RandomPlugin;

public strictfp class Context {
    // Connection to app
    public SimulationListeners simulationListeners;
    public SimulationMonitor simulationMonitor;

    // Plugins
    public RandomPlugin randomPlugin;
    public SleepPlugin sleepPlugin;

    // Gateways
    public GameGateway gameGateway;
    public UnitGateway unitGateway;
    public WaveGateway waveGateway;
    public DifficultyGateway difficultyGateway;
    public MessageGateway messageGateway;
    public PlayerGateway playerGateway;
    public ReplayWriterGateway replayWriterGateway;
    public LocalCommandGateway localCommandGateway;
    public TurnGateway turnGateway;

    // Logic
    public CommandExecutor commandExecutor;
}
