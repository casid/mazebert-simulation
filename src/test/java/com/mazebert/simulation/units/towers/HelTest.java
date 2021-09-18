package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.maps.BloodMoor;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.systems.ProphecySystem;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepModifier;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelTest extends SimTest {

    Wave wave;
    Wizard wizard;
    Hel hel;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        difficultyGateway = new DifficultyGateway();
        playerGateway = new PlayerGatewayTrainer();
        gameGateway = new GameGateway();
        gameGateway.getGame().map = new BloodMoor();
        waveGateway = new WaveGateway();

        randomPlugin = new RandomPluginTrainer();
        gameSystem = new GameSystem();
        prophecySystem = new ProphecySystem();
        lootSystem = new LootSystem();
        experienceSystem = new ExperienceSystem();
        waveSpawner = new WaveSpawner();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        wave = new Wave();
        wave.type = WaveType.Boss;
        wave.creepCount = 1;
        waveGateway.addWave(wave);

        wizard = new Wizard();
        wizard.gold = 100000;
        unitGateway.addUnit(wizard);
    }

    @Test
    void creepGetSlowed() {
        givenHelIsBuiltInFront();
        Creep creep = givenCreepIsSpawned();

        whenCreepReachesHelheim(creep);

        assertThat(creep.getSpeedModifier()).isEqualTo(0.7f);
    }

    @Test
    void creepEscapes() {
        givenHelIsBuiltInFront();
        Creep creep = givenCreepIsSpawned();

        whenCreepReachesHelheim(creep);
        whenCreepEscapesHelheim(creep);

        assertThat(wizard.health).isEqualTo(0.5f);
        assertThat(creep.isDisposed()).isTrue();
    }

    @Test
    void creepReachesBase_baseInHelheim() {
        givenHelIsBuiltInBack();
        Creep creep = givenCreepIsSpawned();

        whenCreepReachesHelheim(creep);
        whenCreepEscapesHelheim(creep);

        assertThat(wizard.health).isEqualTo(0.5f);
        assertThat(creep.isDisposed()).isTrue();
    }

    @Test
    void revivedCreep() {
        givenHelIsBuiltInFront();
        wave.creepModifier1 = CreepModifier.Revive;
        Creep creep = givenCreepIsSpawned();

        whenCreepReachesHelheim(creep);

        hel.kill(creep);
        creep.simulate(Creep.DEATH_TIME);
        assertThat(creep.isDead()).isFalse();
        assertThat(wizard.health).isEqualTo(1.0f); // Wizard does not get damaged!
    }

    Creep givenCreepIsSpawned() {
        waveSpawner.onGameStarted();
        waveSpawner.onUpdate(1);

        return unitGateway.findUnit(Creep.class);
    }

    void givenHelIsBuiltInFront() {
        hel = (Hel)whenTowerIsBuilt(wizard, TowerType.Hel, 20, 15);
    }

    void givenHelIsBuiltInBack() {
        hel = (Hel)whenTowerIsBuilt(wizard, TowerType.Hel, 13, 8);
    }

    void whenCreepReachesHelheim(Creep creep) {
        while (!hel.isInRange(creep, hel.getRange())) {
            creep.simulate(1.0f);
            hel.simulate(1.0f);
        }
    }

    void whenCreepEscapesHelheim(Creep creep) {
        while (hel.isInRange(creep, hel.getRange()) && creep.isPartOfGame()) {
            creep.simulate(1.0f);
            hel.simulate(1.0f);
        }
    }
}