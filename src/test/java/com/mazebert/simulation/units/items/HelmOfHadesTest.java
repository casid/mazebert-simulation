package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class HelmOfHadesTest extends ItemTest {
    @Test
    void rangeIsIncreased() {
        tower.setBaseRange(1);
        whenItemIsEquipped(ItemType.HelmOfHades);
        assertThat(tower.getRange()).isEqualTo(2);
    }

    @Test
    void rangeIsDecreased() {
        tower.setBaseRange(1);
        whenItemIsEquipped(ItemType.HelmOfHades);
        whenItemIsEquipped(null);
        assertThat(tower.getRange()).isEqualTo(1);
    }
}