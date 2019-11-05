package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class KingArthurTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new KingArthur();
    }

    @Test
    void excaliburStatsAreDoubled() {
        whenItemIsEquipped(ItemType.Excalibur);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(1.0f);
        assertThat(tower.getCritDamage()).isEqualTo(0.75f);
        assertThat(tower.getMulticrit()).isEqualTo(5);
    }

    @Test
    void excaliburStatsAreDoubled_droppedCorrectly() {
        whenItemIsEquipped(ItemType.Excalibur);
        whenItemIsEquipped(null);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
        assertThat(tower.getCritDamage()).isEqualTo(0.25f);
        assertThat(tower.getMulticrit()).isEqualTo(1);
    }
}