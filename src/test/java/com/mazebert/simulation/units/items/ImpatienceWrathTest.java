package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.towers.TowerType;
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

    @Test
    void replaceTowerWorksCorrectly() {
        wizard.gold = 10000000;
        whenItemIsEquipped(ItemType.ImpatienceWrathForce, 0);
        whenItemIsEquipped(ItemType.ImpatienceWrathTrain, 1);
        whenItemIsEquipped(ItemType.ImpatienceWrathWatch, 2);

        tower.onAttack.dispatch(null);
        tower.onAttack.dispatch(null);
        tower.onAttack.dispatch(null);
        tower.onAttack.dispatch(null);
        tower.onAttack.dispatch(null);
        tower.onAttack.dispatch(null);

        tower = whenTowerIsReplaced(tower, TowerType.Manitou);
        tower.simulate(10.0f);
        tower.simulate(0.1f);

        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(-0.1f);
    }
}