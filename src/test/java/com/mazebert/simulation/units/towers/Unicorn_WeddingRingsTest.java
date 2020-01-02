package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.systems.WeddingRingSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.units.creeps.CreepBuilder.creep;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

class Unicorn_WeddingRingsTest extends ItemTest {

    Unicorn unicorn;
    TestTower tower1;
    TestTower tower2;

    @Override
    protected Tower createTower() {
        unicorn = new Unicorn();
        return unicorn;
    }

    @BeforeEach
    void setUp() {
        weddingRingSystem = new WeddingRingSystem();

        unicorn.setLevel(50);

        tower1 = new TestTower();
        tower1.setX(1);
        tower1.setWizard(wizard);
        unitGateway.addUnit(tower1);

        tower2 = new TestTower();
        tower2.setX(2);
        tower2.setWizard(wizard);
        unitGateway.addUnit(tower2);

        whenItemIsEquipped(tower1, ItemType.WeddingRing1);
        whenItemIsEquipped(tower2, ItemType.WeddingRing2);

        simulationListeners.onUpdate.dispatch(WeddingRingSystem.SECONDS_FOR_MARRIAGE);

        unitGateway.addUnit(a(creep().withWizard(wizard)));
    }

    @Test
    void v18() {
        version = Sim.vDoL;

        whenPotionIsConsumed(tower1, PotionType.UnicornTears);

        assertThat(tower1.getLevel()).isEqualTo(25);
        assertThat(tower2.getLevel()).isEqualTo(1);
    }

    @Test
    void v19() {
        version = Sim.v19;

        whenPotionIsConsumed(tower1, PotionType.UnicornTears);

        assertThat(tower1.getLevel()).isEqualTo(25);
        assertThat(tower2.getLevel()).isEqualTo(25);
    }
}