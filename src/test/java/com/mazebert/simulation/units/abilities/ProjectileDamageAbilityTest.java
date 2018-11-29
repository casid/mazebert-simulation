package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.Projectile;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class ProjectileDamageAbilityTest extends SimTest {
    ProjectileDamageAbility projectileDamageAbility;

    Tower tower = new TestTower();
    Creep creep = new Creep();

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();
        randomPlugin = new RandomPluginTrainer();
        damageSystem = new DamageSystem(randomPlugin);
        lootSystem = new LootSystemTrainer();
        projectileGateway = new ProjectileGateway();

        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(5.0f);
        tower.setCritChance(0.0f);
        tower.setBaseDamage(10.0f);

        tower.addAbility(new AttackAbility());
        projectileDamageAbility = new ProjectileDamageAbility();
        tower.addAbility(projectileDamageAbility);

        creep.setX(1.0f);
        creep.setWave(new Wave());
        unitGateway.addUnit(creep);
    }

    @Test
    void projectileSpawnsAtTower() {
        tower.setX(2);
        tower.setY(3);

        whenTowerAttacks();

        Projectile projectile = projectileGateway.get(0);
        assertThat(projectile.x).isEqualTo(2);
        assertThat(projectile.y).isEqualTo(3);
    }

    @Test
    void projectileHasSpeed() {
        projectileDamageAbility.setSpeed(0.5f);

        whenTowerAttacks();

        Projectile projectile = projectileGateway.get(0);
        assertThat(projectile.speed).isEqualTo(0.5f);
    }

    @Test
    void projectileIsUpdated_x() {
        creep.setX(1.0f);
        creep.setY(0.0f);
        projectileDamageAbility.setSpeed(0.5f);

        whenTowerAttacks();
        projectileGateway.update(0.1f);

        Projectile projectile = projectileGateway.get(0);
        assertThat(projectile.x).isEqualTo(0.05f);
        assertThat(projectile.y).isEqualTo(0.0f);
    }

    @Test
    void projectileIsUpdated_y() {
        creep.setX(0.0f);
        creep.setY(1.0f);
        projectileDamageAbility.setSpeed(0.5f);

        whenTowerAttacks();
        projectileGateway.update(0.1f);

        Projectile projectile = projectileGateway.get(0);
        assertThat(projectile.x).isEqualTo(0.0f);
        assertThat(projectile.y).isEqualTo(0.05f);
    }

    @Test
    void projectileIsUpdated_diagonal() {
        creep.setX(1.0f);
        creep.setY(1.0f);
        projectileDamageAbility.setSpeed(0.5f);

        whenTowerAttacks();
        projectileGateway.update(0.1f);

        Projectile projectile = projectileGateway.get(0);
        assertThat(projectile.x).isEqualTo(0.03535534f);
        assertThat(projectile.y).isEqualTo(0.03535534f);
    }

    @Test
    void projectileIsUpdated_targetIsHit() {
        projectileDamageAbility.setSpeed(1);
        whenTowerAttacks();

        projectileGateway.update(0.5f); // tick
        thenProjectileAmountIs(1);
        assertThat(creep.getHealth()).isEqualTo(100);

        projectileGateway.update(0.5f); // tack
        thenProjectileAmountIs(0);
        assertThat(creep.getHealth()).isEqualTo(90);
    }

    @Test
    void projectileIsUpdated_targetIsHit_splash() {
        tower.addAbility(new SplashAbility());
        projectileDamageAbility.setSpeed(1);

        whenTowerAttacks();

        projectileGateway.update(0.5f); // tick
        thenProjectileAmountIs(1);
        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void manyProjectiles() {
        for (int i = 0; i < 100; ++i) {
            whenTowerAttacks();
        }

        assertThat(creep.isDead()).isTrue();
        assertThat(creep.getHealth()).isEqualTo(0);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
        projectileGateway.update(0.1f);
    }

    private void thenProjectileAmountIs(int expected) {
        assertThat(projectileGateway.getSize()).isEqualTo(expected);
    }
}