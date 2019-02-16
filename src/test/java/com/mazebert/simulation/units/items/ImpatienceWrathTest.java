package com.mazebert.simulation.units.items;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class ImpatienceWrathTest extends ItemTest {

    @Test
    void incompleteSet() {
        skippedSeconds = 20;

        whenItemIsEquipped(ItemType.ImpatienceWrathWatch, 0);
        whenItemIsEquipped(ItemType.ImpatienceWrathForce, 1);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.25f);
    }

    @Test
    void fullSet() {
        skippedSeconds = 20;

        whenItemIsEquipped(ItemType.ImpatienceWrathWatch, 0);
        whenItemIsEquipped(ItemType.ImpatienceWrathForce, 1);
        whenItemIsEquipped(ItemType.ImpatienceWrathTrain, 3);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.71f);
    }

    @Test
    void fullSet_itemDropped() {
        skippedSeconds = 20;

        whenItemIsEquipped(ItemType.ImpatienceWrathWatch, 0);
        whenItemIsEquipped(ItemType.ImpatienceWrathForce, 1);
        whenItemIsEquipped(ItemType.ImpatienceWrathTrain, 3);
        whenItemIsEquipped(null, 3);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.24999997f);
    }

    @Test
    void skipped() {
        skippedSeconds = 20;

        whenItemIsEquipped(ItemType.ImpatienceWrathWatch, 0);
        whenItemIsEquipped(ItemType.ImpatienceWrathForce, 1);
        whenItemIsEquipped(ItemType.ImpatienceWrathTrain, 3);

        skippedSeconds = 25;
        simulationListeners.onSecondsSkipped.dispatch();

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.72499996f);
    }
}