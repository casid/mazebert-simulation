package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class LightbladeAcademySetTest extends ItemTest {
    @Test
    void completeSet() {
        whenItemIsEquipped(ItemType.LightbladeAcademySword, 0);
        whenItemIsEquipped(ItemType.LightbladeAcademyDrone, 1);
        whenItemIsEquipped(ItemType.BabySword, 4); // one more inventory slot!

        assertThat(tower.getItem(4)).isInstanceOf(BabySword.class);
    }

    @Test
    void completeSet_oneSetItemDropped() {
        whenItemIsEquipped(ItemType.LightbladeAcademySword, 0);
        whenItemIsEquipped(ItemType.LightbladeAcademyDrone, 1);
        whenItemIsEquipped(ItemType.BabySword, 4);
        whenItemIsEquipped(null, 1); // set is no longer complete, no more room for baby sword

        assertThat(tower.getItem(4)).isNull();
        assertThat(tower.getAbility(BabySwordAbility.class)).isNull();
        assertThat(wizard.itemStash.get(ItemType.BabySword).getAmount()).isEqualTo(1);
    }
}