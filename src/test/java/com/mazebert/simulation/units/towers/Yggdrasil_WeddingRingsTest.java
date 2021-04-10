package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Yggdrasil_WeddingRingsTest extends ItemTest {

    Yggdrasil yggdrasil;
    Rabbit otherTower;

    @Override
    protected Tower createTower() {
        yggdrasil = new Yggdrasil();
        return yggdrasil;
    }

    @BeforeEach
    void setUp() {
        weddingRingSystem = new WeddingRingSystem();

        otherTower = new Rabbit();
        otherTower.setX(1);
        otherTower.setWizard(wizard);
        unitGateway.addUnit(otherTower);
    }

    @Test
    void v24() {
        version = Sim.v24;
        givenTowersAreMarried();

        whenPotionIsConsumed(yggdrasil, PotionType.EssenceOfWisdom);

        assertThat(yggdrasil.getLevel()).isEqualTo(10);
        assertThat(otherTower.getLevel()).isEqualTo(10);
    }

    @Test
    void v25() {
        version = Sim.vRoCEnd;
        givenTowersAreMarried();

        whenPotionIsConsumed(yggdrasil, PotionType.EssenceOfWisdom);

        assertThat(yggdrasil.getLevel()).isEqualTo(10);
        assertThat(otherTower.getLevel()).isEqualTo(0);
    }

    void givenTowersAreMarried() {
        whenItemIsEquipped(otherTower, ItemType.WeddingRing1);
        whenItemIsEquipped(yggdrasil, ItemType.WeddingRing2);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
    }
}