package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SplashAbilityTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Tower tower;
    Creep creep1;
    Creep creep2;
    Creep creep3;

    SplashAbility splashAbility;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystem(randomPlugin, simulationListeners, formatPlugin);

        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(5.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
        splashAbility = new SplashAbility();
        splashAbility.setRange(1);
        splashAbility.setDamageFactor(0.4f);
        tower.addAbility(splashAbility);
        tower.setCritChance(0.0f);
        tower.setBaseDamage(10.0f);

        creep1 = new Creep();
        creep1.setX(5);
        creep1.setY(5);
        unitGateway.addUnit(creep1);

        creep2 = new Creep();
        creep2.setX(6);
        creep2.setY(5);
        unitGateway.addUnit(creep2);

        creep3 = new Creep();
        creep3.setX(5);
        creep3.setY(10);
        unitGateway.addUnit(creep3);
    }

    @Test
    void splash() {
        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90.0);
        assertThat(creep2.getHealth()).isEqualTo(95.99999994039536);
        assertThat(creep3.getHealth()).isEqualTo(100.0);
    }

    @Test
    void bigSplash() {
        splashAbility.setRange(20);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90.0);
        assertThat(creep2.getHealth()).isEqualTo(95.99999994039536);
        assertThat(creep3.getHealth()).isEqualTo(95.99999994039536);
    }

    @Test
    void atLeastOneDamage() {
        splashAbility.setDamageFactor(0);

        whenTowerAttacks();

        assertThat(creep2.getHealth()).isEqualTo(99.0);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }

}