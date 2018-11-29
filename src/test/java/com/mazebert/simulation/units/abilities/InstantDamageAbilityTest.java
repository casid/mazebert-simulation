package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.towers.Tower;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class InstantDamageAbilityTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Tower tower;
    Creep creep;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystem(randomPlugin);
        lootSystem = new LootSystemTrainer();

        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(1.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
        tower.setCritChance(0.0f);

        creep = new Creep();
        creep.setWave(new Wave());
        unitGateway.addUnit(creep);
    }

    @Test
    void constantDamage() {
        tower.setBaseDamage(10.0f);
        whenTowerAttacks();
        assertThat(creep.getHealth()).isEqualTo(90.0f);
    }

    @Test
    void spreadDamage_max() {
        randomPluginTrainer.givenFloatAbs(0.999999f);
        tower.setDamageSpread(1.0f);
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isCloseTo(80.0f, Percentage.withPercentage(0.01));
    }

    @Test
    void spreadDamage_min() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        tower.setDamageSpread(1.0f);
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isCloseTo(99.0f, Percentage.withPercentage(0.01));
    }

    @Test
    void constantDamage_crit() {
        tower.setBaseDamage(10.0f);
        tower.setCritChance(0.5f);
        tower.setCritDamage(0.5f);
        randomPluginTrainer.givenFloatAbs(0.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(85.0f);
    }

    @Test
    void constantDamage_crit_unlucky() {
        tower.setBaseDamage(10.0f);
        tower.setCritChance(0.5f);
        tower.setCritDamage(0.5f);
        randomPluginTrainer.givenFloatAbs(0.6f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(90.0f);
    }

    @Test
    void constantDamage_multicrit() {
        tower.setBaseDamage(10.0f);
        tower.setCritChance(0.5f);
        tower.setCritDamage(0.5f);
        tower.setMulticrit(2);
        randomPluginTrainer.givenFloatAbs(0.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_multicrit_2ndRollUnlucky() {
        tower.setBaseDamage(10.0f);
        tower.setCritChance(0.5f);
        tower.setCritDamage(0.5f);
        tower.setMulticrit(3);
        randomPluginTrainer.givenFloatAbs(0.0f, 0.9f, 0.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(85.0f);
    }

    @Test
    void constantDamage_multicrit_probabilityIsDecreased() {
        tower.setBaseDamage(10.0f);
        tower.setCritChance(0.5f);
        tower.setCritDamage(0.5f);
        tower.setMulticrit(10);
        randomPluginTrainer.givenFloatAbs(0.4f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(85.0f);
    }

    @Test
    void constantDamage_addedAbsolute() {
        tower.setBaseDamage(10.0f);
        tower.setAddedAbsoluteBaseDamage(20.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(70.0f);
    }

    @Test
    void constantDamage_addedRelative() {
        tower.setBaseDamage(10.0f);
        tower.setAddedRelativeBaseDamage(0.5f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(85.0f);
    }

    @Test
    void constantDamage_kill() {
        tower.setBaseDamage(1000.0f);

        whenTowerAttacks();

        assertThat(creep.isDead()).isTrue();
        assertThat(creep.getHealth()).isEqualTo(0.0f);
        assertThat(creep.getState()).isEqualTo(CreepState.Death);
    }

    @Test
    void kills() {
        tower.setBaseDamage(1000.0f);

        whenTowerAttacks();

        assertThat(tower.getKills()).isEqualTo(1);
    }

    @Test
    void kills_notDead() {
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(tower.getKills()).isEqualTo(0);
    }

    @Test
    void experience() {
        tower.setBaseDamage(1000.0f);
        creep.setExperience(10);

        whenTowerAttacks();

        assertThat(tower.getExperience()).isEqualTo(10);
    }

    @Test
    void experience_creepModifier() {
        tower.setBaseDamage(1000.0f);
        creep.setExperience(10);
        creep.setExperienceModifier(2);

        whenTowerAttacks();

        assertThat(tower.getExperience()).isEqualTo(20);
    }

    @Test
    void experience_towerModifier() {
        tower.setBaseDamage(1000.0f);
        creep.setExperience(10);
        tower.setExperienceModifier(3);

        whenTowerAttacks();

        assertThat(tower.getExperience()).isEqualTo(30);
    }

    @Test
    void experience_levelUp() {
        tower.setBaseDamage(1000.0f);
        creep.setExperience(20);

        whenTowerAttacks();

        assertThat(tower.getLevel()).isEqualTo(2);
    }

    @Test
    void experience_levelUp_notEnough() {
        tower.setBaseDamage(1000.0f);
        creep.setExperience(19);

        whenTowerAttacks();

        assertThat(tower.getLevel()).isEqualTo(1);
    }

    @Test
    void missChance_miss() {
        tower.setBaseDamage(10.0f);
        tower.setChanceToMiss(0.5f);
        randomPluginTrainer.givenFloatAbs(0.1f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void missChance_hit() {
        tower.setBaseDamage(10.0f);
        tower.setChanceToMiss(0.5f);
        randomPluginTrainer.givenFloatAbs(0.6f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(90);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }
}