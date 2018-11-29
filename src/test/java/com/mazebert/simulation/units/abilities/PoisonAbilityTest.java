package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.PoisonEffect;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class PoisonAbilityTest extends SimTest {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Tower tower;
    Creep creep;

    PoisonAbility poisonAbility;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();
        randomPlugin = randomPluginTrainer;
        damageSystem = new DamageSystem(randomPlugin);

        tower = new TestTower();
        tower.setBaseCooldown(1.0f);
        tower.setBaseRange(5.0f);
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
        poisonAbility = new PoisonAbility(PoisonEffect.class, 3.0f) {
            @Override
            protected double calculatePoisonDamage(Creep target, double damage, int multicrits) {
                return 3 * damage;
            }
        };
        tower.addAbility(poisonAbility);
        tower.setCritChance(0.0f);
        tower.setBaseDamage(10.0f);

        creep = new Creep();
        creep.setX(2);
        creep.setY(2);
        creep.setPath(new Path(2.0f, 2.0f, 2.0f, 3.0f));
        unitGateway.addUnit(creep);
    }

    @Test
    void poisonIsApplied() {
        whenTowerAttacks();
        assertThat(creep.getAbility(PoisonEffect.class)).isNotNull();
    }

    @Test
    void poisonCausesDamageOverTime() {
        whenTowerAttacks();
        assertThat(creep.getHealth()).isEqualTo(90); // initial regular hit

        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(88.99999998509884);
        creep.simulate(1.0f);
        assertThat(creep.getHealth()).isEqualTo(79.33333332339923);
    }

    @Test
    void poisonIsRemoved() {
        whenTowerAttacks();

        creep.simulate(1.0f);
        assertThat(creep.getAbility(PoisonEffect.class)).isNotNull();
        creep.simulate(1.0f);
        assertThat(creep.getAbility(PoisonEffect.class)).isNotNull();
        creep.simulate(1.0f);
        assertThat(creep.getAbility(PoisonEffect.class)).isNull();
    }

    @Test
    void poisonCanStack() {
        whenTowerAttacks();
        whenTowerAttacks();
        assertThat(creep.getHealth()).isEqualTo(80); // initial regular hit

        creep.simulate(0.1f);
        assertThat(creep.getHealth()).isEqualTo(77.99999997019768); // two stacks deal poison now
    }

    private void whenTowerAttacks() {
        tower.simulate(1.0f);
    }
}