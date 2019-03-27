package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.SleepPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import org.junit.jupiter.api.BeforeEach;

public abstract class SimulationTest extends SimTest {

    protected CommandExecutorTrainer commandExecutorTrainer = new CommandExecutorTrainer();
    protected SleepPluginTrainer sleepPluginTrainer = new SleepPluginTrainer();
    protected MessageGatewayTrainer messageGatewayTrainer = new MessageGatewayTrainer();
    protected PlayerGatewayTrainer playerGatewayTrainer = new PlayerGatewayTrainer();
    protected Simulation simulation;
    private int playerCount;

    protected SimulationTest() {
        this(1);
    }

    protected SimulationTest(int playerCount) {
        this.playerCount = playerCount;
    }

    @BeforeEach
    void setUp() {
        commandExecutor = commandExecutorTrainer;
        localCommandGateway = new LocalCommandGateway();
        sleepPlugin = sleepPluginTrainer;
        messageGateway = messageGatewayTrainer;
        playerGateway = playerGatewayTrainer;
        replayWriterGateway = new NoReplayWriterGateway();
        simulationListeners = new SimulationListeners();
        gameGateway = new GameGateway();
        simulationMonitor = new SimulationMonitor();
        unitGateway = new UnitGateway();
        turnGateway = new GameTurnGateway(playerCount);
        projectileGateway = new ProjectileGateway();

        simulation = new Simulation();
    }
}