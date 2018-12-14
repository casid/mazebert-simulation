package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.towers.ScareCrow;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class Trident_ScareCrowTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new ScareCrow();
    }

    @Test
    void initial() {
        whenItemIsEquipped(ItemType.Trident);
        assertThat(tower.getAbility(AttackAbility.class).getTargets()).isEqualTo(4);
    }

    @Test
    void levelUp() {
        whenItemIsEquipped(ItemType.Trident);
        tower.setLevel(14);
        assertThat(tower.getAbility(AttackAbility.class).getTargets()).isEqualTo(5);
    }
}
