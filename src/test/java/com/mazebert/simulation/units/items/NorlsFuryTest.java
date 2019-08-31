package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class NorlsFuryTest extends ItemTest {

    @Test
    void set() {
        NorlsFurySword sword = new NorlsFurySword();
        NorlsFuryAmulet amulet = new NorlsFuryAmulet();
        assertThat(sword.isSet()).isTrue();
        assertThat(amulet.isSet()).isTrue();
    }

    @Test
    void completeSetBonus() {
        whenItemIsEquipped(ItemType.NorlsFurySword, 0);
        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 1);

        assertThat(tower.getCritChance()).isEqualTo(0.15f);
    }

    @Test
    void incomplete1() {
        whenItemIsEquipped(ItemType.NorlsFurySword, 0);
        assertThat(tower.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void incomplete2() {
        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 0);
        assertThat(tower.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void incomplete3() {
        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 0);
        whenItemIsEquipped(ItemType.NorlsFurySword, 0);
        assertThat(tower.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void incomplete4() {
        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 0);
        whenItemIsEquipped(ItemType.BabySword, 1);
        assertThat(tower.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void incomplete5() {
        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 0);
        whenItemIsEquipped(ItemType.NorlsFurySword, 1);
        whenItemIsEquipped(null, 1);
        assertThat(tower.getCritChance()).isEqualTo(0.050000004f);
    }

    @Test
    void oneItemOff() {
        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 0);
        whenItemIsEquipped(ItemType.NorlsFurySword, 1);
        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 2);

        whenItemIsEquipped(null, 2);
        assertThat(tower.getCritChance()).isEqualTo(0.15f);

        whenItemIsEquipped(null, 0);
        assertThat(tower.getCritChance()).isEqualTo(0.050000004f);

        whenItemIsEquipped(ItemType.NorlsFuryAmulet, 0);
        assertThat(tower.getCritChance()).isEqualTo(0.15f);
    }
}