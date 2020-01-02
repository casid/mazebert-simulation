package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class Tinker_WeddingRingTest extends ItemTest {

    Tinker tinker;
    TestTower otherTower;

    @BeforeEach
    void setUp() {
        weddingRingSystem = new WeddingRingSystem();

        otherTower = new TestTower();
        otherTower.setWizard(wizard);
        otherTower.setX(1);
        otherTower.setGender(Gender.Male);
        unitGateway.addUnit(otherTower);

        whenItemIsEquipped(tinker, ItemType.WeddingRing1);
        whenItemIsEquipped(otherTower, ItemType.WeddingRing2);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);
    }

    @Override
    protected Tower createTower() {
        return tinker = new Tinker();
    }

    @Test
    void initialLuck() {
        assertThat(tinker.getLuck()).isEqualTo(1.05f);
    }

    @Test
    void legendary() {
        whenPotionIsConsumed(otherTower, PotionType.ChangeSex);

        assertThat(tinker.getGender()).isEqualTo(Gender.Female);
        assertThat(tinker.getLuck()).isEqualTo(1.05f);
    }

    @Test
    void rarePotions() {
        whenPotionIsConsumed(otherTower, PotionType.RareSpeed);
        whenPotionIsConsumed(otherTower, PotionType.RareSpeed);
        whenPotionIsConsumed(otherTower, PotionType.RareSpeed);
        whenPotionIsConsumed(otherTower, PotionType.RareSpeed);

        assertThat(tinker.getLuck()).isEqualTo(1.05f);
    }
}