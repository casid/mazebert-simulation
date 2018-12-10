package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SniperTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Sniper sniper;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;

        damageSystem = new DamageSystemTrainer();

        sniper = new Sniper();
        unitGateway.addUnit(sniper);
    }

    @Test
    void frustumDamage() {
        randomPluginTrainer.givenFloatAbs(0.7f);

        Creep creep1 = new Creep();
        creep1.setX(-1);
        unitGateway.addUnit(creep1);

        Creep creep2 = new Creep();
        creep2.setX(-2);
        unitGateway.addUnit(creep2);

        Creep creep3 = new Creep();
        creep3.setX(-3);
        creep3.setY(-1);
        unitGateway.addUnit(creep3);

        Creep creep4 = new Creep();
        creep4.setY(-1);
        unitGateway.addUnit(creep4);

        Creep creep5 = new Creep();
        creep5.setY(+1);
        unitGateway.addUnit(creep5);

        whenSniperAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
        assertThat(creep3.getHealth()).isEqualTo(90);
        assertThat(creep4.getHealth()).isEqualTo(100);
        assertThat(creep5.getHealth()).isEqualTo(100);
    }

    @Test
    void stacks() {
        randomPluginTrainer.givenFloatAbs(0, 0, 0, 0.7f); // rolled 3 extra shots

        Creep creep1 = new Creep();
        creep1.setX(-1);
        unitGateway.addUnit(creep1);

        Creep creep2 = new Creep();
        creep2.setX(-2);
        unitGateway.addUnit(creep2);

        whenSniperAttacks();

        assertThat(creep1.getHealth()).isEqualTo(60);
        assertThat(creep2.getHealth()).isEqualTo(60);
    }

    private void whenSniperAttacks() {
        sniper.simulate(sniper.getBaseCooldown());
    }
}