package com.mazebert.simulation.units.items;

import com.mazebert.simulation.systems.WolfSystem;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.Wolf;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class WolfskinTest extends ItemTest {
    @Override
    protected Tower createTower() {
        if (wolfSystem == null) {
            wolfSystem = new WolfSystem();
        }
        return super.createTower();
    }

    @Test
    void equipped() {
        whenItemIsEquipped(ItemType.Wolfskin);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(WolfskinAbility.damageBonus);
        assertThat(tower.getCritChance()).isEqualTo(0.05f + WolfskinAbility.critChanceBonus);
        assertThat(tower.getCritDamage()).isEqualTo(0.25f + WolfskinAbility.critDamageBonus);
    }

    @Test
    void equipped_two() {
        whenItemIsEquipped(ItemType.Wolfskin, 0);
        whenItemIsEquipped(ItemType.Wolfskin, 1);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(2 * WolfskinAbility.damageBonus);
        assertThat(tower.getCritChance()).isEqualTo(0.05f + 2 * WolfskinAbility.critChanceBonus);
        assertThat(tower.getCritDamage()).isEqualTo(0.25f + 2 * WolfskinAbility.critDamageBonus);
    }

    @Test
    void dropped() {
        whenItemIsEquipped(ItemType.Wolfskin);
        whenItemIsEquipped(null);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
        assertThat(tower.getCritChance()).isEqualTo(0.05f);
        assertThat(tower.getCritDamage()).isEqualTo(0.24999999f);
    }

    @Test
    void countsAsWolf() {
        tower.setLevel(10);

        Wolf wolf = new Wolf();
        wolf.setX(1);
        unitGateway.addUnit(wolf);

        whenItemIsEquipped(ItemType.Wolfskin);

        assertThat(wolf.getCritChance()).isEqualTo(0.099999994f);
    }

    @Test
    void countsAsWolf_dropped() {
        tower.setLevel(10);

        Wolf wolf = new Wolf();
        wolf.setX(1);
        unitGateway.addUnit(wolf);

        whenItemIsEquipped(ItemType.Wolfskin);
        whenItemIsEquipped(null);

        assertThat(wolf.getCritChance()).isEqualTo(0.049999997f);
    }

    @Test
    void countsAsWolf_levelChanged() {
        tower.setLevel(10);

        Wolf wolf = new Wolf();
        wolf.setX(1);
        unitGateway.addUnit(wolf);

        whenItemIsEquipped(ItemType.Wolfskin);
        tower.setLevel(20);

        assertThat(wolf.getCritChance()).isEqualTo(0.14999999f);
    }
}