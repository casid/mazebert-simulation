package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.*;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.creeps.CreepType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class InstantDamageAbilityTest extends SimTest {

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;
    Tower tower;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystem();
        lootSystem = new LootSystemTrainer();
        experienceSystem = new ExperienceSystem();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        tower.setWizard(wizard);
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(1.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
        tower.setCritChance(0.0f);
        unitGateway.addUnit(tower);

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
    void constantDamage_damageAgainstBer() {
        creep.getWave().armorType = ArmorType.Ber;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstBer(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstFal() {
        creep.getWave().armorType = ArmorType.Fal;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstFal(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstVex() {
        creep.getWave().armorType = ArmorType.Vex;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstVex(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstZod() {
        creep.getWave().armorType = ArmorType.Zod;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstZod(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstBosses() {
        creep.getWave().type = WaveType.Boss;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstBosses(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstBosses_challenge() {
        creep.getWave().type = WaveType.Challenge;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstBosses(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstBosses_horseman() {
        creep.getWave().type = WaveType.Horseman;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstBosses(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstBosses_timelord() {
        creep.getWave().type = WaveType.TimeLord;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstBosses(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageAgainstAir() {
        creep.getWave().type = WaveType.Air;
        tower.setBaseDamage(10.0f);
        tower.addDamageAgainstAir(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_damageModifier() {
        creep.setDamageModifier(2.0f);
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(80.0f);
    }

    @Test
    void constantDamage_armor() {
        creep.setArmor(100);
        tower.setBaseDamage(100.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(33.33333333333333);
    }

    @Test
    void constantDamage_armor_penetration_quarter() {
        creep.setArmor(100);
        tower.setBaseDamage(100.0f);
        tower.addArmorPenetration(0.25f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(25.0);
    }

    @Test
    void constantDamage_armor_penetration_half() {
        creep.setArmor(100);
        tower.setBaseDamage(100.0f);
        tower.addArmorPenetration(0.5f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(16.666666666666657);
    }

    @Test
    void constantDamage_armor_penetration_full() {
        creep.setArmor(100);
        tower.setBaseDamage(100.0f);
        tower.addArmorPenetration(1.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(0);
    }

    @Test
    void constantDamage_armor_penetration_doubled_cap() {
        creep.setArmor(100);
        tower.setBaseDamage(100.0f);
        tower.addArmorPenetration(2.0f);
        creep.setMaxHealth(200);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void constantDamage_armor_penetration_negative_ignored() {
        creep.setArmor(100);
        tower.setBaseDamage(100.0f);
        tower.addArmorPenetration(-0.5f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(33.33333333333333);
    }

    @Test
    void constantDamage_armor_negative() {
        creep.setArmor(-100);
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(84.47885719403078);
    }

    @Test
    void constantDamage_armor_negative_penetrationHasNoEffect_ignored() {
        creep.setArmor(-100);
        tower.setBaseDamage(10.0f);
        tower.addArmorPenetration(0.5f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(84.47885719403078);
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
    void constantDamage_timeLord() {
        creep.setImmortal(true);
        tower.setBaseDamage(1000.0f);

        whenTowerAttacks();

        assertThat(creep.isDead()).isFalse();
        assertThat(creep.getHealth()).isEqualTo(100.0f);
        assertThat(creep.getState()).isEqualTo(CreepState.Running);
        assertThat(tower.getKills()).isEqualTo(0);
    }

    @Test
    void instantKill_timeLord() {
        creep.setImmortal(true);
        tower.setBaseDamage(1000.0f);

        tower.kill(creep);

        assertThat(creep.isDead()).isFalse();
        assertThat(creep.getHealth()).isEqualTo(100.0f);
        assertThat(creep.getState()).isEqualTo(CreepState.Running);
        assertThat(tower.getKills()).isEqualTo(0);
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
        randomPluginTrainer.givenFloatAbs(0.9f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void missChance_hit() {
        tower.setBaseDamage(10.0f);
        tower.setChanceToMiss(0.5f);
        randomPluginTrainer.givenFloatAbs(0.4f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(90);
    }

    @Test
    void missChance_creep_miss() {
        tower.setBaseDamage(10.0f);
        creep.addChanceToMiss(0.5f);
        randomPluginTrainer.givenFloatAbs(0.9f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(100);
    }

    @Test
    void missChance_creep_hit() {
        tower.setBaseDamage(10.0f);
        creep.addChanceToMiss(0.5f);
        randomPluginTrainer.givenFloatAbs(0.4f);

        whenTowerAttacks();

        assertThat(creep.getHealth()).isEqualTo(90);
    }

    @Test
    void stats() {
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(tower.getBestHit()).isEqualTo(10);
        assertThat(tower.getTotalDamage()).isEqualTo(10);
    }

    @Test
    void stats_bestHitMustBeBetter() {
        tower.setBestHit(100);
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(tower.getBestHit()).isEqualTo(100);
    }

    @Test
    void stats_totalDamageIsAdded() {
        tower.setTotalDamage(1000);
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(tower.getTotalDamage()).isEqualTo(1010);
    }

    @Test
    void stats_wizard() {
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(wizard.bestHit).isEqualTo(10);
        assertThat(wizard.totalDamage).isEqualTo(10);
    }

    @Test
    void stats_wizard_bestHitMustBeBetter() {
        wizard.bestHit = 100;
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(wizard.bestHit).isEqualTo(100);
    }

    @Test
    void stats_wizard_totalDamageIsAdded() {
        wizard.totalDamage = 1000;
        tower.setBaseDamage(10.0f);

        whenTowerAttacks();

        assertThat(wizard.totalDamage).isEqualTo(1010);
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }
}