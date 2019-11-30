package com.mazebert.simulation.units.items;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public strictfp class GuardLanceTest extends ItemTest {
    @Test
    void damage() {
        whenItemIsEquipped(ItemType.GuardLance);
        Assertions.assertThat(tower.getAddedAbsoluteBaseDamage()).isEqualTo(4);
        Assertions.assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.2f);
    }

    @Test
    void damage_stacks() {
        whenItemIsEquipped(ItemType.GuardLance, 0);
        whenItemIsEquipped(ItemType.GuardLance, 1);
        Assertions.assertThat(tower.getAddedAbsoluteBaseDamage()).isEqualTo(8);
        Assertions.assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.4f);
    }

    @Test
    void damage_stacks_removed() {
        whenItemIsEquipped(ItemType.GuardLance, 0);
        whenItemIsEquipped(ItemType.GuardLance, 1);
        whenItemIsEquipped(null, 1);
        Assertions.assertThat(tower.getAddedAbsoluteBaseDamage()).isEqualTo(4);
        Assertions.assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.2f);
    }
}