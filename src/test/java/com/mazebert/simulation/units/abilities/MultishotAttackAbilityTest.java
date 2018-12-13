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

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class MultishotAttackAbilityTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Tower tower;
    Creep creep1;
    Creep creep2;
    Creep creep3;

    AttackAbility multishot = new AttackAbility();

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystem();

        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(5.0f);
        multishot = new AttackAbility();
        multishot.setTargets(2);
        tower.addAbility(multishot);
        tower.addAbility(new InstantDamageAbility());
        tower.setCritChance(0.0f);
        tower.setBaseDamage(10.0f);

        creep1 = a(creep());
        creep1.setX(1);
        creep1.setY(1);
        unitGateway.addUnit(creep1);

        creep2 = a(creep());
        creep2.setX(2);
        creep2.setY(1);
        unitGateway.addUnit(creep2);

        creep3 = a(creep());
        creep3.setX(3);
        creep3.setY(1);
        unitGateway.addUnit(creep3);
    }

    @Test
    void twoTargets() {
        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
        assertThat(creep3.getHealth()).isEqualTo(100);
    }

    @Test
    void threeTargets() {
        multishot.setTargets(3);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
        assertThat(creep3.getHealth()).isEqualTo(90);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }
}