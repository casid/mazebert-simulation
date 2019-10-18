package com.mazebert.simulation.units.creeps.effects;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.WaveSpawner;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.plugins.ClientPluginTrainer;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.CreepState;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public strictfp class ReviveEffectTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();
    DamageSystemTrainer damageSystemTrainer = new DamageSystemTrainer();

    Tower tower;
    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        gameGateway = new GameGateway();
        waveGateway = new WaveGateway();
        damageSystem = damageSystemTrainer;
        damageSystemTrainer.givenConstantDamage(1000);
        lootSystem = new LootSystemTrainer();
        experienceSystem = new ExperienceSystem();
        waveSpawner = new WaveSpawner();
        clientPlugin = new ClientPluginTrainer();

        tower = new TestTower();
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
        unitGateway.addUnit(tower);

        creep = a(creep());
        creep.addAbility(new ReviveEffect());
        creep.setExperience(10);
        creep.setGold(23);
        creep.setMinDrops(1);
        creep.setMaxDrops(2);
        creep.onDead.add(waveSpawner);
        unitGateway.addUnit(creep);
    }

    @Test
    void resurrect() {
        whenTowerAttacks();
        creep.simulate(Creep.DEATH_TIME);

        assertThat(creep.getState()).isEqualTo(CreepState.Running);
        assertThat(creep.getHealth()).isEqualTo(50);
        assertThat(creep.getMaxHealth()).isEqualTo(50);
        assertThat(creep.getGold()).isEqualTo(0);
        assertThat(creep.getExperience()).isEqualTo(0);
        assertThat(creep.getMinDrops()).isEqualTo(0);
        assertThat(creep.getMaxDrops()).isEqualTo(0);
        assertThat(unitGateway.hasUnit(creep)).isTrue();
    }

    @Test
    void resurrect_notInfinitly() {
        for (int i = 0; i < 5; ++i) {
            whenTowerAttacks();
            creep.simulate(Creep.DEATH_TIME);
        }

        thenCreepIsNotRevived();
    }

    @Test
    void resurrect_chance() {
        randomPluginTrainer.givenFloatAbs(0.5f);

        whenTowerAttacks();
        creep.simulate(Creep.DEATH_TIME);

        thenCreepIsNotRevived();
    }

    @Test
    void resurrect_notWhenRestingInPiece() {
        creep.setRestsInPiece(true);

        whenTowerAttacks();
        creep.simulate(Creep.DEATH_TIME);

        thenCreepIsNotRevived();
    }

    private void whenTowerAttacks() {
        tower.simulate(tower.getCooldown());
    }

    private void thenCreepIsNotRevived() {
        assertThat(unitGateway.hasUnit(creep)).isFalse();
    }
}