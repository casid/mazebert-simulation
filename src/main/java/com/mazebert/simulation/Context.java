package com.mazebert.simulation;

import com.mazebert.simulation.countdown.*;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.FollowPathResult;
import com.mazebert.simulation.plugins.ClientPlugin;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.LogPlugin;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.*;
import com.mazebert.simulation.systems.DamageSystem.DamageInfo;
import com.mazebert.simulation.units.Currency;
import com.mazebert.simulation.units.heroes.Hero;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumMap;

public strictfp class Context {
    // For visuals only, so static is ok here
    public static Currency currency = Currency.Gold;

    // Current simulation version
    public int version = Sim.version;
    public boolean season;

    // Connection to app
    public SimulationListeners simulationListeners;
    public SimulationMonitor simulationMonitor;

    // Plugins
    public UuidRandomPlugin randomPlugin;
    public SleepPlugin sleepPlugin;
    public FormatPlugin formatPlugin;
    public ClientPlugin clientPlugin;
    public LogPlugin logPlugin;

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
    public ProphecySystem prophecySystem;
    public CommandExecutor commandExecutor;
    public Simulation simulation;

    // Temp instances
    public GameCountDown gameCountDown;
    public WaveCountDown waveCountDown;
    public BonusRoundCountDown bonusRoundCountDown;
    public TimeLordCountDown timeLordCountDown;
    public EarlyCallCountDown earlyCallCountDown;
    public StallingPreventionCountDown stallingPreventionCountDown;
    public WaveSpawner waveSpawner;
    public final float[] tempChancesForRarity = new float[Rarity.VALUES.length];
    public int skippedSeconds;
    public EnumMap<TowerType, Tower> towerInstances = new EnumMap<>(TowerType.class);
    public EnumMap<ItemType, Item> itemInstances = new EnumMap<>(ItemType.class);
    public EnumMap<PotionType, Potion> potionInstances = new EnumMap<>(PotionType.class);
    public EnumMap<HeroType, Hero> heroInstances = new EnumMap<>(HeroType.class);
    public DamageInfo damageInfo = new DamageInfo();
    public FollowPathResult followPathResult = new FollowPathResult();
    public boolean newBalancing;

    public void init(Simulation simulation) {
        damageSystem = new DamageSystem();
        lootSystem = new LootSystem();
        experienceSystem = new ExperienceSystem();
        wolfSystem = new WolfSystem();
        pubSystem = new PubSystem();
        weddingRingSystem = new WeddingRingSystem();
        prophecySystem = new ProphecySystem();
        gameSystem = new GameSystem();

        this.simulation = simulation;
        commandExecutor.init();
    }
}
