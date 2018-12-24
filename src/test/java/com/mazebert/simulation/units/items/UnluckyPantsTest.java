package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class UnluckyPantsTest extends ItemTest {
    @Test
    void equipped() {
        whenItemIsEquipped(ItemType.UnluckyPants);
        assertThat(tower.getLuck()).isEqualTo(0.8f);
    }

    @Test
    void equipped_otherTowerInRange() {
        Tower otherTower = new TestTower();
        otherTower.setY(-1);
        unitGateway.addUnit(otherTower);

        whenItemIsEquipped(ItemType.UnluckyPants);

        assertThat(otherTower.getLuck()).isEqualTo(1.2f);
    }
}