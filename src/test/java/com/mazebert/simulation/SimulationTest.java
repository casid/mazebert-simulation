package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.SleepPluginTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.jusecase.inject.ComponentTest;
import org.jusecase.inject.Trainer;

public abstract class SimulationTest implements ComponentTest {

    @Trainer
    protected CommandExecutorTrainer simulationCommandExecutor;
    @Trainer
    protected LocalCommandGateway localCommandGateway;
    @Trainer
    protected SleepPluginTrainer sleepPluginTrainer;
    @Trainer
    protected MessageGatewayTrainer messageGatewayTrainer;
    @Trainer
    protected PlayerGatewayTrainer playerGatewayTrainer;
    @Trainer
    protected NoReplayWriterGateway noReplayWriterGateway;
    @Trainer
    protected SimulationListeners simulationListeners;
    @Trainer
    protected GameGateway gameGateway;

    private int playerCount;
    protected TurnGateway turnGateway;

    protected Simulation simulation;

    protected SimulationTest() {
        this(1);
    }

    protected SimulationTest(int playerCount) {
        this.playerCount = playerCount;
    }

    @BeforeEach
    void setUp() {
        turnGateway = new TurnGateway(playerCount);
        givenDependency(turnGateway);
        givenDependency(new UnitGateway());

        simulation = new Simulation();
    }
}