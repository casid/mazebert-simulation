package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystem;

import java.util.EnumMap;

public strictfp class Context {
    // Connection to app
    public SimulationListeners simulationListeners;
    public SimulationMonitor simulationMonitor;

    // Plugins
    public UuidRandomPlugin randomPlugin;
    public SleepPlugin sleepPlugin;
    public FormatPlugin formatPlugin;

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
    public ProjectileGateway projectileGateway;

    // Logic
    public DamageSystem damageSystem;
    public CommandExecutor commandExecutor;
    public final float[] tempChancesForRarity = new float[Rarity.values().length];
}
