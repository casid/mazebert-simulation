package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class KnusperHexeTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    KnusperHexe knusperHexe;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();

        randomPlugin = randomPluginTrainer;

        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();

        knusperHexe = new KnusperHexe();
        unitGateway.addUnit(knusperHexe);

        creep = a(creep());
        unitGateway.addUnit(creep);
    }

    @Test
    void aura() {
        assertThat(creep.getArmor()).isEqualTo(-10);
    }

    @Test
    void auraLeft() {
        creep.setX(4);
        knusperHexe.simulate(0.1f);
        assertThat(creep.getArmor()).isEqualTo(0);
    }

    @Test
    void eat() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        creep.getWave().type = WaveType.Mass;

        whenKnusperHexeAttacks();

        assertThat(creep.isDead()).isTrue();
    }

    @Test
    void eat_notEveryTime() {
        randomPluginTrainer.givenFloatAbs(0.3f);
        creep.getWave().type = WaveType.Mass;

        whenKnusperHexeAttacks();

        assertThat(creep.isDead()).isFalse();
    }

    @Test
    void eat_onlyMass() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        creep.getWave().type = WaveType.Air;

        whenKnusperHexeAttacks();

        assertThat(creep.isDead()).isFalse();
    }

    @Test
    void eat_affectsAura() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        creep.getWave().type = WaveType.Mass;
        whenKnusperHexeAttacks();

        Creep anotherCreep = a(creep());
        unitGateway.addUnit(anotherCreep);

        assertThat(anotherCreep.getArmor()).isEqualTo(-11);
    }

    private void whenKnusperHexeAttacks() {
        knusperHexe.simulate(knusperHexe.getBaseCooldown());
    }
}