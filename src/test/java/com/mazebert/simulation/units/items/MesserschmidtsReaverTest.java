package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MesserschmidtsReaverTest extends ItemTest {
    @Test
    void adjustments() {
        tower.setBaseRange(4);
        whenItemIsEquipped(ItemType.MesserschmidtsReaver);
        assertThat(tower.getRange()).isEqualTo(2.4f);
    }
}