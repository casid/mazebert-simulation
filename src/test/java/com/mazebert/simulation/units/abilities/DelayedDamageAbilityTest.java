package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DelayedDamageAbilityTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Tower tower;
    Creep creep;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();
        turnGateway = new TurnGateway(1);
        randomPlugin = randomPluginTrainer;

        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(1.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new DelayedDamageAbility(0.5f));
        tower.setCritChance(0.0f);
        tower.setBaseDamage(10.0f);

        creep = new Creep();
        unitGateway.addUnit(creep);
    }

    @Test
    void creepNotInstantlyDamaged() {
        whenTowerAttacks();
        thenCreepIsNotDamaged();
    }

    @Test
    void creepNotInstantlyDamaged_delayNotYetReached() {
        whenTowerAttacks();
        whenTurnIsSimulated(0.45f);

        thenCreepIsNotDamaged();
    }

    @Test
    void creepDamagedAfterDelay() {
        whenTowerAttacks();
        whenTurnIsSimulated(0.5f);

        thenCreepIsDamaged();
    }

    @Test
    void superFastTower_noDelay() {
        tower.setAttackSpeedAdd(9.0f);
        whenTurnIsSimulated(1.0f);
        assertThat(creep.getHealth()).isEqualTo(10.0f);
    }

    private void whenTowerAttacks() {
        whenTurnIsSimulated(1.0f);
    }

    private void whenTurnIsSimulated(float dt) {
        tower.simulate(dt);
        turnGateway.incrementTurnNumber();
    }

    private void thenCreepIsDamaged() {
        assertThat(creep.getHealth()).isEqualTo(90.0f);
    }

    private void thenCreepIsNotDamaged() {
        assertThat(creep.getHealth()).isEqualTo(100.0f);
    }
}