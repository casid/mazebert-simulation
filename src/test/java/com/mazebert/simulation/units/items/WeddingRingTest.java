package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.towers.Yggdrasil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class WeddingRingTest extends ItemTest {
    SimulationListenersTrainer simulationListenersTrainer = new SimulationListenersTrainer();
    Tower otherTower;

    @BeforeEach
    void setUp() {
        wizard.gold = 100000;

        simulationListeners = simulationListenersTrainer;
        playerGateway = new PlayerGatewayTrainer();
        weddingRingSystem = new WeddingRingSystem();

        otherTower = new TestTower();
        otherTower.setWizard(wizard);
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
    void married_tears() {
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
        whenPotionIsConsumed(tower, PotionType.Tears);

        assertThat(tower.getMulticrit()).isEqualTo(2);
        assertThat(otherTower.getMulticrit()).isEqualTo(2);
    }

    @Test
    void married_notification() {
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);
        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);

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

    @Test
    void yggdrasil_1() {
        tower.setElement(Element.Nature);
        Yggdrasil yggdrasil = new Yggdrasil();
        yggdrasil.setWizard(wizard);
        yggdrasil.setX(2);
        unitGateway.addUnit(yggdrasil);

        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(yggdrasil, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);

        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilLegacy, 1);
        whenPotionIsConsumed(yggdrasil, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.08f);
    }

    @Test
    void yggdrasil_2() {
        tower.setElement(Element.Nature);
        Yggdrasil yggdrasil = new Yggdrasil();
        yggdrasil.setWizard(wizard);
        yggdrasil.setX(2);
        unitGateway.addUnit(yggdrasil);

        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(yggdrasil, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);

        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilLegacy, 1);
        whenPotionIsConsumed(tower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void yggdrasil_3() {
        tower.setElement(Element.Nature);
        Yggdrasil yggdrasil = new Yggdrasil();
        yggdrasil.setWizard(wizard);
        yggdrasil.setX(2);
        unitGateway.addUnit(yggdrasil);

        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);

        whenItemIsEquipped(yggdrasil, ItemType.BranchOfYggdrasilLegacy, 0);
        whenItemIsEquipped(yggdrasil, ItemType.BranchOfYggdrasilLegacy, 1);
        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilLegacy, 1);
        whenItemIsEquipped(otherTower, ItemType.BranchOfYggdrasilLegacy, 1);
        whenPotionIsConsumed(tower, PotionType.CommonSpeed);

        assertThat(tower.getAttackSpeedAdd()).isEqualTo(0.04f);
        assertThat(otherTower.getAttackSpeedAdd()).isEqualTo(0.04f);
    }

    @Test
    void yggdrasil_tears() {
        tower.setElement(Element.Nature);
        Yggdrasil yggdrasil = new Yggdrasil();
        yggdrasil.setWizard(wizard);
        yggdrasil.setX(2);
        unitGateway.addUnit(yggdrasil);

        whenItemIsEquipped(tower, ItemType.WeddingRing1, 0);
        whenItemIsEquipped(yggdrasil, ItemType.WeddingRing2, 0);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);

        whenItemIsEquipped(tower, ItemType.BranchOfYggdrasilLegacy, 1);
        whenPotionIsConsumed(yggdrasil, PotionType.Tears);

        assertThat(tower.getMulticrit()).isEqualTo(3);
    }
}