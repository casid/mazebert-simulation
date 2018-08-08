package com.mazebert.simulation;

import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.plugins.SleepPluginTrainer;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.ComponentTest;
import org.jusecase.inject.Trainer;

import static com.mazebert.simulation.messages.TurnBuilder.turn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

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