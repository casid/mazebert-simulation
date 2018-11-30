package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VikingTest extends SimTest {
    Viking viking;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();
        projectileGateway = new ProjectileGateway();
        damageSystem = new DamageSystemTrainer();

        viking = new Viking();
        unitGateway.addUnit(viking);
    }

    @Test
    void attack_canHitSameCreepWithTwoAxesAtOnce() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        viking.simulate(3.0f); // attack
        projectileGateway.simulate(1.0f); // projectile spawning
        projectileGateway.simulate(1.0f); // projectile hitting

        assertThat(creep.getHealth()).isEqualTo(80);
    }
}