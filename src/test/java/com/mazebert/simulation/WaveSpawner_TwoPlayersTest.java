package com.mazebert.simulation;

import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepModifier;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.creeps.effects.UnionEffect;
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

        assertThat(unitGateway.getAmount(Creep.class)).isEqualTo(0); // players share the same boss
        assertThat(wizard1.health).isEqualTo(0.75f);
        assertThat(wizard2.health).isEqualTo(0.75f);
    }

    @Test
    void health_round1() {
        givenBossWave();
        whenGameIsStarted();
        whenGameIsUpdated();
        Creep boss = getCreep(0);

        assertThat(boss.getHealth()).isEqualTo(409.6);
    }

    @Test
    void modifier_union() {
        Wave wave = new Wave();
        wave.round = 1;
        wave.creepCount = 1;
        wave.type = WaveType.Boss;
        wave.creepType = CreepType.Orc;
        wave.creepModifier1 = CreepModifier.Union;
        waveGateway.addWave(wave);

        whenAllCreepsAreSpawned();

        assertThat(getCreep(0).getHealth()).isEqualTo(409.6); // all creeps have the same health (the rounds health pool * player count)
        assertThat(getCreep(0).getAbility(UnionEffect.class)).isNotNull();
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

    private void whenAllCreepsAreSpawned() {
        simulationListeners.onGameStarted.dispatch();
        for (int i = 0; i < 10; ++i) {
            simulationListeners.onUpdate.dispatch(1.0f);
        }
    }

    private void whenGameIsUpdated() {
        whenGameIsUpdated(1.0f);
    }
}