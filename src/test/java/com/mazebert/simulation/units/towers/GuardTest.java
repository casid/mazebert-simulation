package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.Gender;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class GuardTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        damageSystem = new DamageSystemTrainer();
        randomPlugin = randomPluginTrainer = new RandomPluginTrainer();
    }

    @Test
    void oneGuard() {
        Guard guard = new Guard();
        unitGateway.addUnit(guard);

        assertThat(guard.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    @Test
    void twoGuards() {
        Guard guard1 = new Guard();
        unitGateway.addUnit(guard1);
        Guard guard2 = new Guard();
        unitGateway.addUnit(guard2);

        assertThat(guard1.getAddedAbsoluteBaseDamage()).isEqualTo(2);
        assertThat(guard2.getAddedAbsoluteBaseDamage()).isEqualTo(2);
    }

    @Test
    void threeGuards() {
        Guard guard1 = new Guard();
        unitGateway.addUnit(guard1);
        Guard guard2 = new Guard();
        unitGateway.addUnit(guard2);
        Guard guard3 = new Guard();
        unitGateway.addUnit(guard3);

        assertThat(guard1.getAddedAbsoluteBaseDamage()).isEqualTo(4);
        assertThat(guard2.getAddedAbsoluteBaseDamage()).isEqualTo(4);
        assertThat(guard3.getAddedAbsoluteBaseDamage()).isEqualTo(4);
    }

    @Test
    void otherTower() {
        Guard guard = new Guard();
        unitGateway.addUnit(guard);
        Adventurer adventurer = new Adventurer();
        unitGateway.addUnit(adventurer);

        assertThat(guard.getAddedAbsoluteBaseDamage()).isEqualTo(0);
        assertThat(adventurer.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    @Test
    void guardOutOfRange() {
        Guard guard1 = new Guard();
        unitGateway.addUnit(guard1);
        Guard guard2 = new Guard();
        guard2.setX(4);
        unitGateway.addUnit(guard2);

        assertThat(guard1.getAddedAbsoluteBaseDamage()).isEqualTo(0);
        assertThat(guard2.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    @Test
    void guardRemoved() {
        Guard guard1 = new Guard();
        unitGateway.addUnit(guard1);
        Guard guard2 = new Guard();
        unitGateway.addUnit(guard2);
        unitGateway.removeUnit(guard2);

        assertThat(guard1.getAddedAbsoluteBaseDamage()).isEqualTo(0);
        assertThat(guard2.getAddedAbsoluteBaseDamage()).isEqualTo(0);
    }

    @Test
    void female() {
        randomPluginTrainer.givenFloatAbs(0.1f);
        Guard guard = new Guard();
        unitGateway.addUnit(guard);

        assertThat(guard.getGender()).isEqualTo(Gender.Female);
    }

    @Test
    void male() {
        randomPluginTrainer.givenFloatAbs(0.9f);
        Guard guard = new Guard();
        unitGateway.addUnit(guard);

        assertThat(guard.getGender()).isEqualTo(Gender.Male);
    }
}