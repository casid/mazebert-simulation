package com.mazebert.simulation.units.towers;


import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class BlueWhaleTest extends ItemTest {

    Creep creep1;
    Creep creep2;
    Creep creep3;

    @BeforeEach
    void setUp() {
        creep1 = a(creep());
        creep1.setX(1);
        creep1.setY(0);
        unitGateway.addUnit(creep1);

        creep2 = a(creep());
        creep2.setX(2);
        creep2.setY(0);
        unitGateway.addUnit(creep2);

        creep3 = a(creep());
        creep3.setX(5);
        creep3.setY(10);
        unitGateway.addUnit(creep3);
    }

    @Override
    protected Tower createTower() {
        projectileGateway = new ProjectileGateway();
        return new BlueWhale();
    }

    @Test
    void splash() {
        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90.0);
        assertThat(creep2.getHealth()).isEqualTo(97.99999997019768);
        assertThat(creep3.getHealth()).isEqualTo(100.0);
    }

    @Test
    void splash_noCrit() {
        whenTowerAttacks();

        assertThat(creep1.getState()).isEqualTo(CreepState.Running);
        assertThat(creep2.getState()).isEqualTo(CreepState.Running);
        assertThat(creep3.getState()).isEqualTo(CreepState.Running);
    }

    @Test
    void splash_crit() {
        damageSystemTrainer.givenConstantCrit(1);

        whenTowerAttacks();

        assertThat(creep1.getState()).isEqualTo(CreepState.Hit);
        assertThat(creep2.getState()).isEqualTo(CreepState.Hit);
        assertThat(creep3.getState()).isEqualTo(CreepState.Running);
    }

    @Override
    protected void whenTowerAttacks() {
        super.whenTowerAttacks();
        whenGameProjectilesAreSimulated(10); // Wait for impact...
    }
}