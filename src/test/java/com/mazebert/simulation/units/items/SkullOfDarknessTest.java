package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class SkullOfDarknessTest extends ItemTest {

    Creep creep;

    @BeforeEach
    void setUp() {
        creep = a(creep());
        unitGateway.addUnit(creep);

        tower.addAbility(new AttackAbility());
        tower.addAbility(new InstantDamageAbility());

        damageSystemTrainer.givenConstantDamage(1000);
    }

    @Test
    void creepNotKilled() {
        whenItemIsEquipped(ItemType.SkullOfDarkness);
        assertThat(wizard.health).isEqualTo(1.0f);
    }

    @Test
    void creepKilled() {
        whenItemIsEquipped(ItemType.SkullOfDarkness);
        whenTowerAttacks();
        assertThat(wizard.health).isEqualTo(1.01f);
    }

    @Test
    void badLuck() {
        randomPluginTrainer.givenFloatAbs(0);
        whenItemIsEquipped(ItemType.SkullOfDarkness);
        whenTowerAttacks();
        assertThat(wizard.health).isEqualTo(1.01f);
    }

    @Test
    void itemIsDestroyed() {
        whenItemIsEquipped(ItemType.SkullOfDarkness);

        for (int i = 0; i < 105; ++i) {
            creep = a(creep());
            unitGateway.addUnit(creep);
            whenTowerAttacks();
        }

        assertThat(wizard.health).isEqualTo(1.999999f);
        assertThat(tower.getItem(0)).isNull();
    }

    private void whenTowerAttacks() {
        tower.simulate(tower.getCooldown());
    }
}