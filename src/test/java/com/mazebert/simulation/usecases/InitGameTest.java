package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.mazebert.simulation.Balancing.GAME_COUNTDOWN_SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class InitGameTest extends UsecaseTest<InitGameCommand> {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    boolean gameStarted;

    @BeforeEach
    void setUp() {
        randomPlugin = randomPluginTrainer;
        simulationListeners = new SimulationListeners();
        waveGateway = new WaveGateway();
        gameGateway = new GameGateway();
        unitGateway = new UnitGateway();
        difficultyGateway = new DifficultyGateway();

        usecase = new InitGame();

        request.rounds = 1;
    }

    @Test
    void seedIsSet() {
        request.gameId = UUID.randomUUID();
        whenRequestIsExecuted();
        randomPluginTrainer.thenSeedIsSetTo(request.gameId);
    }

    @Test
    void healthIsSet() {
        whenRequestIsExecuted();
        assertThat(gameGateway.getGame().health).isEqualTo(1.0f);
    }

    @Test
    void gameCountDownIsStarted() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isTrue();
        assertThat(simulationListeners.onUpdate.size()).isEqualTo(1);
    }

    @Test
    void gameCountDownIsStarted_notEnoughTimePassed() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.5f * GAME_COUNTDOWN_SECONDS);

        assertThat(gameStarted).isFalse();
    }

    @Test
    void gameCountDownIsStarted_noUpdateYet() {
        simulationListeners.onGameStarted.add(() -> gameStarted = true);

        whenRequestIsExecuted();

        assertThat(gameStarted).isFalse();
    }

    @Test
    void gameCountDownIsUpdated() {
        List<Integer> countDown = new ArrayList<>();
        simulationListeners.onGameCountDown.add(countDown::add);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(1.0f);

        assertThat(countDown).containsExactly((int) GAME_COUNTDOWN_SECONDS, (int) GAME_COUNTDOWN_SECONDS - 1);
    }

    @Test
    void gameCountDownIsUpdated_onlyWhenSecondsChange() {
        List<Integer> countDown = new ArrayList<>();
        simulationListeners.onGameCountDown.add(countDown::add);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.25f);
        simulationListeners.onUpdate.dispatch(0.25f);
        simulationListeners.onUpdate.dispatch(0.25f);
        simulationListeners.onUpdate.dispatch(0.25f);

        assertThat(countDown).containsExactly((int) GAME_COUNTDOWN_SECONDS, (int) GAME_COUNTDOWN_SECONDS - 1);
    }

    @Test
    void gameCountDownIsUpdated_rounding() {
        List<Integer> countDown = new ArrayList<>();
        simulationListeners.onGameCountDown.add(countDown::add);

        whenRequestIsExecuted();
        simulationListeners.onUpdate.dispatch(0.5f);
        simulationListeners.onUpdate.dispatch(0.5001f);

        assertThat(countDown).containsExactly((int) GAME_COUNTDOWN_SECONDS, (int) GAME_COUNTDOWN_SECONDS - 1);
    }

    @Test
    void wavesAreGenerated() {
        request.rounds = 250;
        whenRequestIsExecuted();
        assertThat(waveGateway.getWaves()).hasSize(WaveGateway.WAVES_IN_ADVANCE);
    }

    @Test
    void wavesAreGenerated_notMoreThanTotalWaveCount() {
        request.rounds = 2;
        whenRequestIsExecuted();
        assertThat(waveGateway.getWaves()).hasSize(request.rounds);
    }

    @Test
    void waveGeneration_normal() {
        randomPluginTrainer.givenFloatAbs(0.0f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.nextWave();
        assertThat(wave.type).isEqualTo(WaveType.Normal);
        assertThat(wave.creepCount).isEqualTo(10);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(1.0f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(1.6f);
    }

    @Test
    void waveGeneration_mass() {
        randomPluginTrainer.givenFloatAbs(0.26f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.nextWave();
        assertThat(wave.type).isEqualTo(WaveType.Mass);
        assertThat(wave.creepCount).isEqualTo(20);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(0.2f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(0.6f);
    }

    @Test
    void waveGeneration_boss() {
        randomPluginTrainer.givenFloatAbs(0.51f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.nextWave();
        assertThat(wave.type).isEqualTo(WaveType.Boss);
        assertThat(wave.creepCount).isEqualTo(1);
    }

    @Test
    void waveGeneration_air() {
        randomPluginTrainer.givenFloatAbs(0.76f);

        whenRequestIsExecuted();

        Wave wave = waveGateway.nextWave();
        assertThat(wave.type).isEqualTo(WaveType.Air);
        assertThat(wave.creepCount).isEqualTo(5);
        assertThat(wave.minSecondsToNextCreep).isEqualTo(1.6f);
        assertThat(wave.maxSecondsToNextCreep).isEqualTo(3.2f);
    }

    @Test
    void gameId() {
        request.gameId = UUID.fromString("86ce2065-ce3a-46a8-ab3f-ac3179b62002");

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().id).isEqualTo(request.gameId);
    }

    @Test
    void noRounds_meansWizardTower() {
        request.rounds = 0;
        whenRequestIsExecuted();
        assertThat(simulationListeners.onUpdate.size()).isEqualTo(0);
    }

    @Test
    void firstGame() {
        whenRequestIsExecuted();

        Wizard wizard = unitGateway.getWizard();
        assertThat(wizard).isNotNull();
        assertThat(wizard.towerStash.size()).isEqualTo(1);
        assertThat(wizard.towerStash.get(0).getCardType()).isEqualTo(TowerType.Hitman);
        assertThat(wizard.towerStash.get(0).getAmount()).isEqualTo(4);
    }

    @Test
    void secondGame() {
        Wizard existingWizard = new Wizard();
        unitGateway.addUnit(existingWizard);

        whenRequestIsExecuted();

        assertThat(unitGateway.getUnits()).hasSize(1);
        Wizard wizard = unitGateway.getWizard();
        assertThat(wizard).isSameAs(existingWizard);
    }

    @Test
    void map() {
        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().map).isInstanceOf(BloodMoor.class);
    }
}