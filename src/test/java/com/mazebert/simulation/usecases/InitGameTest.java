package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.Trainer;

import static org.assertj.core.api.Assertions.assertThat;

class InitGameTest extends UsecaseTest<InitGameCommand> {

    @Trainer
    RandomPluginTrainer randomPluginTrainer;
    @Trainer
    SimulationListeners simulationListeners;

    boolean gameStarted;

    @BeforeEach
    void setUp() {
        usecase = new InitGame();
    }

    @Test
    void seedIsSet() {
        request.randomSeed = 1234;
        whenRequestIsExecuted();
        randomPluginTrainer.thenSeedIsSetTo(1234);
    }

    @Test
    void gameCountDownIsStarted() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(Balancing.GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isTrue();
        assertThat(simulationListeners.onUpdate.getListeners()).isEmpty();
    }

    @Test
    void gameCountDownIsStarted_notEnoughTimePassed() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.5f * Balancing.GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isFalse();
    }

    @Test
    void gameCountDownIsStarted_noUpdateYet() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();

        assertThat(gameStarted).isFalse();
    }
}