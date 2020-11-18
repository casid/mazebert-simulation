package com.mazebert.simulation.units.items;

import com.mazebert.simulation.gateways.WaveGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EldritchPearlTest extends ItemTest {

    @BeforeEach
    void setUp() {
        waveGateway = new WaveGateway();
    }

    @Test
    void onlyPearl() {
        whenItemIsEquipped(ItemType.EldritchPearl);
        assertThat(tower.getLuck()).isEqualTo(1.2f);
        assertThat(tower.getMulticrit()).isEqualTo(2);
    }

    @Test
    void pearlAndClam() {
        whenItemIsEquipped(ItemType.EldritchPearl, 0);
        whenItemIsEquipped(ItemType.EldritchClam, 1);

        assertThat(tower.getLuck()).isEqualTo(1.0f);
        assertThat(tower.getMulticrit()).isEqualTo(3);
    }

    @Test
    void clamAndPearl() {
        whenItemIsEquipped(ItemType.EldritchClam, 0);
        whenItemIsEquipped(ItemType.EldritchPearl, 1);

        assertThat(tower.getLuck()).isEqualTo(1.0f);
        assertThat(tower.getMulticrit()).isEqualTo(3);
    }

    @Test
    void clamPearlRifleAndNecklace() {
        whenItemIsEquipped(ItemType.EldritchClam, 0);
        whenItemIsEquipped(ItemType.EldritchPearl, 1);
        whenItemIsEquipped(ItemType.EldritchMarshRifle, 2);
        whenItemIsEquipped(ItemType.EldritchMarshNecklace, 3);

        assertThat(tower.getMulticrit()).isEqualTo(5);
    }

    @Test
    void clamAndPearl_clamDropped() {
        whenItemIsEquipped(ItemType.EldritchClam, 0);
        whenItemIsEquipped(ItemType.EldritchPearl, 1);
        whenItemIsEquipped(null, 0);

        assertThat(tower.getLuck()).isEqualTo(1.2f);
        assertThat(tower.getMulticrit()).isEqualTo(2);
    }

    @Test
    void clamAndPearl_pearlDropped() {
        whenItemIsEquipped(ItemType.EldritchClam, 0);
        whenItemIsEquipped(ItemType.EldritchPearl, 1);
        whenItemIsEquipped(null, 1);

        assertThat(tower.getLuck()).isEqualTo(0.8f);
        assertThat(tower.getMulticrit()).isEqualTo(1);
    }
}