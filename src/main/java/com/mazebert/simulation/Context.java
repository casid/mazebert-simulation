package com.mazebert.simulation;

import com.mazebert.simulation.countdown.EarlyCallCountDown;
import com.mazebert.simulation.countdown.GameCountDown;
import com.mazebert.simulation.countdown.WaveCountDown;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.ClientPlugin;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.*;
import com.mazebert.simulation.units.Currency;

public strictfp class Context {
    public static Currency currency = Currency.Gold;

    // Connection to app
    public SimulationListeners simulationListeners;
    public SimulationMonitor simulationMonitor;

    // Plugins
    public UuidRandomPlugin randomPlugin;
    public SleepPlugin sleepPlugin;
    public FormatPlugin formatPlugin;
    public ClientPlugin clientPlugin;

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
    public GameSystem gameSystem;
    public DamageSystem damageSystem;
    public LootSystem lootSystem;
    public ExperienceSystem experienceSystem;
    public WolfSystem wolfSystem;
    public PubSystem pubSystem;
    public WeddingRingSystem weddingRingSystem;
    public CommandExecutor commandExecutor;
    public Simulation simulation;

    // Temp instances
    public GameCountDown gameCountDown;
    public WaveCountDown waveCountDown;
    public EarlyCallCountDown earlyCallCountDown;
    public WaveSpawner waveSpawner;
    public final float[] tempChancesForRarity = new float[Rarity.values().length];
    public int skippedSeconds;

    public void init(Simulation simulation) {
        gameSystem = new GameSystem();
        damageSystem = new DamageSystem();
        lootSystem = new LootSystem();
        experienceSystem = new ExperienceSystem();
        wolfSystem = new WolfSystem();
        pubSystem = new PubSystem();
        weddingRingSystem = new WeddingRingSystem();

        this.simulation = simulation;
        commandExecutor.init();
    }
}
