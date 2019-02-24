package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class WaveSpawner_TwoPlayersTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();
    PlayerGatewayTrainer playerGatewayTrainer = new PlayerGatewayTrainer();

    WaveSpawner waveSpawner;

    Wizard wizard1;
    Wizard wizard2;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();

        formatPlugin = new FormatPlugin();

        unitGateway = new UnitGateway();
        playerGateway = playerGatewayTrainer;
        waveGateway = new WaveGateway();
        randomPlugin = randomPluginTrainer;
        difficultyGateway = new DifficultyGateway();
        gameGateway = new GameGateway();
        lootSystem = new LootSystem();
        experienceSystem = new ExperienceSystem();
        gameSystem = new GameSystem();

        gameGateway.getGame().map = new BloodMoor();

        waveSpawner = new WaveSpawner();
        waveGateway.setTotalWaves(250);

        playerGatewayTrainer.givenPlayerCount(2);

        wizard1 = new Wizard();
        wizard1.playerId = 1;
        unitGateway.addUnit(wizard1);
        wizard2 = new Wizard();
        wizard2.playerId = 2;
        unitGateway.addUnit(wizard2);
    }

    @Test
    void creepReachesTarget() {
        givenBossWave();
        whenGameIsStarted();
        whenGameIsUpdated();
        Creep creep1 = getCreep(0);
        creep1.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));
        creep1.simulate(1.0f);

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(1); // creep of wizard 2
        assertThat(wizard1.health).isEqualTo(0.5f);
        assertThat(wizard2.health).isEqualTo(1.0f);

        Creep creep2 = getCreep(0);
        creep2.setPath(new Path(0.0f, 0.0f, 0.0f, 1.0f));
        creep2.simulate(1.0f);

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0); // all gone
        assertThat(wizard1.health).isEqualTo(0.5f);
        assertThat(wizard2.health).isEqualTo(0.5f);
    }

    private Creep getCreep(int index) {
        return (Creep) unitGateway.getUnit(2 + index); // first two units are our wizards
    }

    private void givenBossWave() {
        Wave wave = new Wave();
        wave.creepCount = 1;
        wave.type = WaveType.Boss;
        wave.round = 1;
        waveGateway.addWave(wave);
    }

    private void whenGameIsStarted() {
        simulationListeners.onGameStarted.dispatch();
        simulationListeners.onUpdate.dispatch(0.0f);
    }

    private void whenGameIsUpdated(float dt) {
        simulationListeners.onUpdate.dispatch(dt);
    }

    private void whenGameIsUpdated() {
        whenGameIsUpdated(1.0f);
    }
}