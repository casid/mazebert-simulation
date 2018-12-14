package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MesserschmidtsReaverTest extends ItemTest {
    @Test
    void rangeIsReduced() {
        tower.setBaseRange(4);
        whenItemIsEquipped(ItemType.MesserschmidtsReaver);
        assertThat(tower.getRange()).isEqualTo(2.4f);
    }

    @Test
    void rangeIsReduced_notBelow1() {
        tower.setBaseRange(1);
        whenItemIsEquipped(ItemType.MesserschmidtsReaver);
        assertThat(tower.getRange()).isEqualTo(1);
    }
}