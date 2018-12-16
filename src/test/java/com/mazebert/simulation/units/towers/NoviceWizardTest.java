package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class NoviceWizardTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    NoviceWizard noviceWizard;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();
        damageSystem = new DamageSystemTrainer();
        randomPlugin = randomPluginTrainer;

        noviceWizard = new NoviceWizard();
        unitGateway.addUnit(noviceWizard);

        creep = new Creep();
        creep.setPath(new Path(0, 3, 3, 3, 3, 0));
        creep.setX(0);
        creep.setY(3);
        unitGateway.addUnit(creep);
    }

    @Test
    void warp_normalCreepMovement() {
        creep.simulate(1.0f);
        assertThat(creep.getX()).isEqualTo(1);
        assertThat(creep.getY()).isEqualTo(3);
    }

    @Test
    void warp_forward() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // spell triggered
                0.3f, // time warp
                0.0f // warp forward
        );

        whenNoviceWizardAttacks();

        assertThat(creep.getX()).isEqualTo(3);
        assertThat(creep.getY()).isEqualTo(3);
    }

    @Test
    void warp_backward() {
        creep.simulate(3.0f);
        randomPluginTrainer.givenFloatAbs(
                0.0f, // spell triggered
                0.3f, // time warp
                0.9f // warp backward
        );

        whenNoviceWizardAttacks();

        assertThat(creep.getX()).isEqualTo(0);
        assertThat(creep.getY()).isEqualTo(3);
    }

    @Test
    void warp_backward_aroundCorner() {
        creep.simulate(1.0f);
        creep.simulate(1.0f);
        creep.simulate(1.0f);
        creep.simulate(1.0f);
        randomPluginTrainer.givenFloatAbs(
                0.0f, // spell triggered
                0.3f, // time warp
                0.9f // warp backward
        );

        whenNoviceWizardAttacks();

        assertThat(creep.getX()).isEqualTo(1);
        assertThat(creep.getY()).isEqualTo(3);
    }

    @Test
    void warp_backward_notLessThanStart() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // spell triggered
                0.3f, // time warp
                0.9f // warp backward
        );

        whenNoviceWizardAttacks();

        assertThat(creep.getX()).isEqualTo(0);
        assertThat(creep.getY()).isEqualTo(3);
    }

    @Test
    void banish_good() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // spell triggered
                0.6f, // banish
                0.9f // more damage
        );

        whenNoviceWizardAttacks();

        assertThat(creep.getDamageModifier()).isEqualTo(1.5f);
    }

    @Test
    void banish_good_doesNotStack() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // spell triggered
                0.6f, // banish
                0.9f, // more damage
                0.0f, // spell triggered
                0.6f, // banish
                0.9f // more damage
        );

        whenNoviceWizardAttacks();
        whenNoviceWizardAttacks();

        assertThat(creep.getDamageModifier()).isEqualTo(1.5f);
    }

    @Test
    void banish_bad() {
        randomPluginTrainer.givenFloatAbs(
                0.0f, // spell triggered
                0.6f, // banish
                0.0f // less damage
        );

        whenNoviceWizardAttacks();

        assertThat(creep.getDamageModifier()).isEqualTo(0.5f);
    }

    private void whenNoviceWizardAttacks() {
        noviceWizard.simulate(noviceWizard.getBaseCooldown());
        projectileGateway.simulate(0.5f);
        projectileGateway.simulate(0.5f);
    }
}