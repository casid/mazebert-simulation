package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.towers.Mummy;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class UnionEffectTest extends SimTest {

    DamageSystemTrainer damageSystemTrainer;
    RandomPluginTrainer randomPluginTrainer;

    Tower tower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        damageSystem = damageSystemTrainer = new DamageSystemTrainer();
        randomPlugin = randomPluginTrainer = new RandomPluginTrainer();
        experienceSystem = new ExperienceSystem();
        lootSystem = new LootSystemTrainer();
        projectileGateway = new ProjectileGateway();

        tower = new TestTower();
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
        unitGateway.addUnit(tower);
    }

    @Test
    void oneCreep() {
        Creep creep1 = a(creep());
        creep1.addAbility(new UnionEffect());
        unitGateway.addUnit(creep1);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
    }

    @Test
    void twoCreeps() {
        Creep creep1 = a(creep());
        creep1.addAbility(new UnionEffect());
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.addAbility(new UnionEffect());
        creep2.setWave(creep1.getWave());
        unitGateway.addUnit(creep2);

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(90);
    }

    @Test
    void instantKill() {
        Creep creep1 = a(creep());
        creep1.addAbility(new UnionEffect());
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.addAbility(new UnionEffect());
        creep2.setWave(creep1.getWave());
        unitGateway.addUnit(creep2);

        tower = new Mummy();

        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(0);
        assertThat(creep2.getHealth()).isEqualTo(100);
    }

    @Test
    void instantKill_dot() {
        version = Sim.v19;
        Creep creep1 = a(creep());
        creep1.addAbility(new UnionEffect());
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.addAbility(new UnionEffect());
        creep2.setWave(creep1.getWave());
        unitGateway.addUnit(creep2);

        tower = new Mummy();

        whenTowerAttacks();
        damageSystemTrainer.dealDamage(tower, tower, creep2);

        assertThat(tower.getExperience()).isEqualTo(2); // mummy kill counts only once!
    }

    @Test
    void revive() {
        Creep creep1 = a(creep());
        creep1.addAbility(new UnionEffect());
        creep1.addAbility(new ReviveEffect());
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.addAbility(new UnionEffect());
        creep2.addAbility(new ReviveEffect());
        creep2.setWave(creep1.getWave());
        unitGateway.addUnit(creep2);

        damageSystemTrainer.givenConstantDamage(100);

        whenTowerAttacks();
        creep1.simulate(Creep.DEATH_TIME);
        creep2.simulate(Creep.DEATH_TIME);

        assertThat(creep1.getState()).isEqualTo(CreepState.Running);
        assertThat(creep1.getHealth()).isEqualTo(50);
        assertThat(creep1.getMaxHealth()).isEqualTo(50);

        assertThat(creep2.getState()).isEqualTo(CreepState.Running);
        assertThat(creep2.getHealth()).isEqualTo(50);
        assertThat(creep2.getMaxHealth()).isEqualTo(50);
    }

    @Test
    void revive_chance() {
        Creep creep1 = a(creep());
        creep1.addAbility(new UnionEffect());
        creep1.addAbility(new ReviveEffect());
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.addAbility(new UnionEffect());
        creep2.addAbility(new ReviveEffect());
        creep2.setWave(creep1.getWave());
        unitGateway.addUnit(creep2);

        damageSystemTrainer.givenConstantDamage(100);
        randomPluginTrainer.givenFloatAbs(0.5f);

        whenTowerAttacks();
        creep1.simulate(Creep.DEATH_TIME);
        creep2.simulate(Creep.DEATH_TIME);

        assertThat(creep1.getState()).isEqualTo(CreepState.Dead);
        assertThat(creep2.getState()).isEqualTo(CreepState.Dead);
    }

    @Test
    void experience() {
        damageSystemTrainer.givenConstantDamage(1000);

        Creep creep1 = a(creep());
        creep1.addAbility(new UnionEffect());
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.addAbility(new UnionEffect());
        creep2.setWave(creep1.getWave());
        unitGateway.addUnit(creep2);

        whenTowerAttacks();

        assertThat(tower.getExperience()).isEqualTo(4);
    }

    void whenTowerAttacks() {
        tower.simulate(tower.getBaseCooldown());
    }
}