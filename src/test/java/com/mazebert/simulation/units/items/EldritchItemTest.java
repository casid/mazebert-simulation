package com.mazebert.simulation.units.items;

import com.mazebert.simulation.gateways.WaveGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EldritchItemTest extends ItemTest {

    @BeforeEach
    void setUp() {
        waveGateway = new WaveGateway();
    }

    @Test
    void itemEquipped() {
        whenItemIsEquipped(ItemType.EldritchClam);
        assertThat(waveGateway.getCultistChance()).isEqualTo(1.01f);
    }

    @Test
    void itemEquippedAndDropped() {
        whenItemIsEquipped(ItemType.EldritchClam);
        whenItemIsEquipped(null);
        assertThat(waveGateway.getCultistChance()).isEqualTo(1.0f);
    }

    @Test
    void multipleItemsEquipped() {
        whenItemIsEquipped(ItemType.EldritchClam, 0);
        whenItemIsEquipped(ItemType.EldritchClam, 1);
        whenItemIsEquipped(ItemType.EldritchClam, 2);
        whenItemIsEquipped(ItemType.EldritchClam, 3);
        assertThat(waveGateway.getCultistChance()).isEqualTo(1.04f);
    }

    @Test
    void itemEquipped_dagon() {
        tower.addEldritchCardModifier(1.0f);
        whenItemIsEquipped(ItemType.EldritchClam);
        assertThat(waveGateway.getCultistChance()).isEqualTo(1.02f);
    }
}