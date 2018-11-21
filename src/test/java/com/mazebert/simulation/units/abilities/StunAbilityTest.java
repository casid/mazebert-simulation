package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Path;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StunAbilityTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Tower tower;
    Creep creep;

    StunAbility stunAbility;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;

        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(5.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new DamageAbility());
        stunAbility = new StunAbility();
        stunAbility.setChance(0.2f);
        stunAbility.setDuration(1.0f);
        tower.addAbility(stunAbility);
        tower.setCritChance(0.0f);
        tower.setBaseDamage(10.0f);

        creep = new Creep();
        creep.setX(2);
        creep.setY(2);
        creep.setPath(new Path(2.0f, 2.0f, 2.0f, 3.0f));
        unitGateway.addUnit(creep);

        randomPluginTrainer.givenFloatAbs(0.1f);
    }

    @Test
    void stun() {
        whenTowerAttacks();
        assertThat(creep.getState()).isEqualTo(CreepState.Hit);
    }

    @Test
    void stun_durationPassed() {
        whenTowerAttacks();

        assertThat(creep.getState()).isEqualTo(CreepState.Hit);
        creep.simulate(stunAbility.getDuration());
        assertThat(creep.getState()).isEqualTo(CreepState.Running);
        assertThat(creep.getAbilities()).isEmpty();
    }

    @Test
    void stun_durationNotPassed() {
        whenTowerAttacks();

        assertThat(creep.getState()).isEqualTo(CreepState.Hit);
        creep.simulate(0.8f * stunAbility.getDuration());
        assertThat(creep.getState()).isEqualTo(CreepState.Hit);
    }

    @Test
    void stun_notRolled() {
        randomPluginTrainer.givenFloatAbs(0.9f);

        whenTowerAttacks();

        assertThat(creep.getState()).isEqualTo(CreepState.Running);
    }

    @Test
    void stun_notForDeadCreeps() {
        creep.setHealth(0);

        whenTowerAttacks();

        assertThat(creep.getState()).isEqualTo(CreepState.Death);
    }

    @Test
    void stun_increasedByLuck() {
        randomPluginTrainer.givenFloatAbs(2 * stunAbility.getChance());
        tower.setLuck(2.0f);

        whenTowerAttacks();

        assertThat(creep.getState()).isEqualTo(CreepState.Hit);
    }

    @Test
    void stun_cannotGoBeyondMaxTriggerChance() {
        randomPluginTrainer.givenFloatAbs(Balancing.MAX_TRIGGER_CHANCE + 0.01f);
        tower.setLuck(100.0f);
        whenTowerAttacks();
        assertThat(creep.getState()).isEqualTo(CreepState.Running);
    }

    @Test
    void splashCannotStun() {
        SplashAbility splashAbility = new SplashAbility();
        splashAbility.setRange(10);
        splashAbility.setDamageFactor(0.5f);
        tower.addAbility(splashAbility);
        Creep splashCreep = new Creep();
        splashCreep.setX(3);
        splashCreep.setX(3);
        unitGateway.addUnit(splashCreep);

        whenTowerAttacks();

        assertThat(creep.getState()).isEqualTo(CreepState.Hit);
        assertThat(splashCreep.getState()).isEqualTo(CreepState.Running);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }
}