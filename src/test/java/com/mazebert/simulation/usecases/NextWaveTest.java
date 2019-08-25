package com.mazebert.simulation.usecases;

import com.mazebert.simulation.*;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.countdown.BonusRoundCountDown;
import com.mazebert.simulation.countdown.TimeLordCountDown;
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
    private int timeLordStarted;

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
        simulationListeners.onTimeLordStarted.add(() -> ++timeLordStarted);
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
        Wave wave = new Wave();
        wave.creepCount = 1;
        wave.type = WaveType.Boss;
        waveGateway.addWave(wave);

        whenRequestIsExecuted();

        assertThat(skippedSeconds).isEqualTo(5);
    }

    @Test
    void skippedSeconds_evenIfSuperEarly_onlyIfThereIsAWaveToSkip() {
        // No next wave

        whenRequestIsExecuted();

        assertThat(skippedSeconds).isEqualTo(0);
    }

    @Test
    void bonusRound() {
        gameGateway.getGame().bonusRound = true;
        bonusRoundCountDown = new BonusRoundCountDown();

        whenRequestIsExecuted();

        assertThat(bonusRoundStarted).isEqualTo(1);
        assertThat(skippedSeconds).isEqualTo(60);
    }

    @Test
    void timeLord() {
        gameGateway.getGame().timeLord = true;
        timeLordCountDown = new TimeLordCountDown();

        whenRequestIsExecuted();

        assertThat(timeLordStarted).isEqualTo(1);
        assertThat(skippedSeconds).isEqualTo((int)Balancing.TIME_LORD_COUNTDOWN_SECONDS);
    }

    @Test
    void timeLord_bonusRound() {
        gameGateway.getGame().bonusRoundSeconds = 5000;
        gameGateway.getGame().timeLord = true;
        timeLordCountDown = new TimeLordCountDown();

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(5000 + (int)Balancing.TIME_LORD_COUNTDOWN_SECONDS);
    }

    @Test
    void timeLord_bonusRound_passed() {
        gameGateway.getGame().bonusRoundSeconds = 5000;
        gameGateway.getGame().timeLord = true;
        timeLordCountDown = new TimeLordCountDown();
        timeLordCountDown.onUpdate(10);

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().bonusRoundSeconds).isEqualTo(5000 + (int)Balancing.TIME_LORD_COUNTDOWN_SECONDS - 10);
    }
}