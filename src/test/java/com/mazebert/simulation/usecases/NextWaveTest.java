package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.countdown.BonusRoundCountDown;
import com.mazebert.simulation.countdown.WaveCountDown;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.GameSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NextWaveTest extends UsecaseTest<NextWaveCommand> {
    private int waveStarted;
    private int bonusRoundStarted;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = new RandomPluginTrainer();
        difficultyGateway = new DifficultyGateway();
        waveGateway = new WaveGateway();
        gameGateway = new GameGateway();
        playerGateway = new PlayerGatewayTrainer();
        gameSystem = new GameSystem();
        waveSpawner = new WaveSpawner();

        usecase = new NextWave();

        simulationListeners.onWaveStarted.add(() -> ++waveStarted);
        simulationListeners.onBonusRoundStarted.add(() -> ++bonusRoundStarted);
    }

    @Test
    void start() {
        whenRequestIsExecuted();
        assertThat(waveStarted).isEqualTo(1);
    }

    @Test
    void multipleTimes() {
        whenRequestIsExecuted();
        whenRequestIsExecuted();

        assertThat(waveStarted).isEqualTo(1); // Extra command is ignored
    }

    @Test
    void skippedSeconds() {
        waveCountDown = new WaveCountDown();
        whenRequestIsExecuted();
        assertThat(skippedSeconds).isEqualTo(5);
    }

    @Test
    void skippedSeconds_evenIfSuperEarly() {
        whenRequestIsExecuted();
        assertThat(skippedSeconds).isEqualTo(5);
    }

    @Test
    void bonusRound() {
        gameGateway.getGame().bonusRound = true;
        bonusRoundCountDown = new BonusRoundCountDown();

        whenRequestIsExecuted();

        assertThat(bonusRoundStarted).isEqualTo(1);
        assertThat(skippedSeconds).isEqualTo(60);
    }
}