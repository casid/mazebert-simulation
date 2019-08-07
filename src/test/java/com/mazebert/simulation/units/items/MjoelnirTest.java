package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.UnionEffect;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class MjoelnirTest extends ItemTest {
    @BeforeEach
    void setUp() {
        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());
    }

    @Test
    void noChainLightningAmokWithSplash() {
        Creep creep1 = a(creep());
        Creep creep2 = a(creep());
        Creep creep3 = a(creep());

        unitGateway.addUnit(creep1);
        unitGateway.addUnit(creep2);
        unitGateway.addUnit(creep3);

        whenItemIsEquipped(ItemType.Mjoelnir, 0);
        whenItemIsEquipped(ItemType.MesserschmidtsReaver, 1);
        whenTowerAttacks();

        assertThat(creep1.getHealth()).isEqualTo(90);
        assertThat(creep2.getHealth()).isEqualTo(97.5);
    }

    @Test
    void cullingStrike() {
        Creep creep = a(creep());
        creep.setHealth(12);
        unitGateway.addUnit(creep);

        whenItemIsEquipped(ItemType.Mjoelnir, 0);
        whenTowerAttacks();

        assertThat(creep.isDead()).isTrue();
    }

    @Test
    void cullingStrike_union() {
        Creep creep1 = a(creep());
        creep1.setHealth(17);
        creep1.addAbility(new UnionEffect());
        unitGateway.addUnit(creep1);

        Creep creep2 = a(creep());
        creep2.setHealth(17);
        creep2.addAbility(new UnionEffect());
        creep2.setWave(creep1.getWave());
        unitGateway.addUnit(creep2);

        whenItemIsEquipped(ItemType.Mjoelnir, 0);
        whenTowerAttacks();

        assertThat(creep1.isDead()).isTrue();
        assertThat(creep2.isDead()).isTrue();
    }
}