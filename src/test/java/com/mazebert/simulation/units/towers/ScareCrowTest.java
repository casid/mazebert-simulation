package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class ScareCrowTest extends SimTest {
    private ScareCrow scareCrow;
    private Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();

        damageSystem = new DamageSystemTrainer();

        scareCrow = new ScareCrow();
        unitGateway.addUnit(scareCrow);

        creep = new Creep();
        unitGateway.addUnit(creep);
    }

    @Test
    void initiallyTwoCrows() {
        whenScareCrowAttacks();
        assertThat(creep.getHealth()).isEqualTo(80);
    }

    @Test
    void crowsNeedToReturnInOrderToAttackAgain() {
        whenScareCrowAttacks(); // crows attack
        assertThat(creep.getHealth()).isEqualTo(80);
        whenScareCrowAttacks(); // crows return
        assertThat(creep.getHealth()).isEqualTo(80);
        whenScareCrowAttacks(); // crows attack
        assertThat(creep.getHealth()).isEqualTo(60);
    }

    @Test
    void moreCrowsOnLevelUp() {
        scareCrow.setLevel(14);
        whenScareCrowAttacks();
        assertThat(creep.getHealth()).isEqualTo(70);
    }

    private void whenScareCrowAttacks() {
        scareCrow.simulate(scareCrow.getBaseCooldown());
        projectileGateway.simulate(0.1f);
        projectileGateway.simulate(0.1f);
    }
}