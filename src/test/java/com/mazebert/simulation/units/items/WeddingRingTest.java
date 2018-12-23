package com.mazebert.simulation.units.items;

import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class WeddingRingTest extends ItemTest {
    SimulationListenersTrainer simulationListenersTrainer = new SimulationListenersTrainer();
    Tower otherTower;

    @BeforeEach
    void setUp() {
        simulationListeners = simulationListenersTrainer;
        weddingRingSystem = new WeddingRingSystem();

        otherTower = new TestTower();
        otherTower.setX(1);
        unitGateway.addUnit(otherTower);
    }

    @Test
    void notYetMarried() {
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);

        whenPotionIsConsumed(tower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(otherTower.getAttackSpeedAdd()).isEqualTo(0.0f);
    }

    @Test
    void married1() {
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        whenPotionIsConsumed(tower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(otherTower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void married2() {
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        whenPotionIsConsumed(otherTower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(otherTower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void married_notification() {
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        whenPotionIsConsumed(otherTower, PotionType.CommonSpeed);

        simulationListenersTrainer.thenNotificationsAre(tower, "Just married!");
        simulationListenersTrainer.thenNotificationsAre(otherTower, "Just married!");
    }

    @Test
    void countdown() {
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);

        simulationListeners.onUpdate.dispatch(0.5f);

        simulationListenersTrainer.thenNotificationsAre(tower, "20");
        simulationListenersTrainer.thenNotificationsAre(otherTower, "20");
        simulationListeners.onUpdate.dispatch(0.5f);
        simulationListenersTrainer.thenNotificationsAre(tower, "20", "19");
        simulationListenersTrainer.thenNotificationsAre(otherTower, "20", "19");
        simulationListeners.onUpdate.dispatch(0.5f);
        simulationListeners.onUpdate.dispatch(0.5f);
        simulationListenersTrainer.thenNotificationsAre(tower, "20", "19", "18");
        simulationListenersTrainer.thenNotificationsAre(otherTower, "20", "19", "18");
    }

    @Test
    void married_ItemRemoved1() {
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        whenItemIsEquipped(otherTower, null, 0);
        whenPotionIsConsumed(tower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(otherTower.getAttackSpeedAdd()).isEqualTo(0.0f);
    }

    @Test
    void married_ItemRemoved2() {
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        whenItemIsEquipped(tower, null, 0);
        whenPotionIsConsumed(otherTower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.0f);
        assertThat(otherTower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void married_towerRemoved() {
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        otherTower = whenTowerIsReplaced(otherTower, TowerType.Beaver);

        whenPotionIsConsumed(tower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(otherTower.getAttackSpeedAdd()).isEqualTo(0.0f);
    }
}