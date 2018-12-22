package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class FistfulOfSteelTest extends ItemTest {
    @Test
    void critBonus() {
        whenItemIsEquipped(ItemType.FistfulOfSteel);
        assertThat(tower.getCritChance()).isEqualTo(0.1f);
    }

    @Test
    void critBonus_otherTowerInRange() {
        Tower tower = new TestTower();
        tower.setX(1);
        unitGateway.addUnit(tower);

        whenItemIsEquipped(ItemType.FistfulOfSteel);

        assertThat(tower.getCritChance()).isEqualTo(0.1f);
    }

    @Test
    void critBonus_otherTowerOutOfRange() {
        Tower tower = new TestTower();
        tower.setX(2);
        unitGateway.addUnit(tower);

        whenItemIsEquipped(ItemType.FistfulOfSteel);

        assertThat(tower.getCritChance()).isEqualTo(0.05f);
    }

    @Test
    void multipleItems() {
        whenItemIsEquipped(ItemType.FistfulOfSteel, 0);
        whenItemIsEquipped(ItemType.FistfulOfSteel, 1);
        whenItemIsEquipped(ItemType.FistfulOfSteel, 2);
        assertThat(tower.getCritChance()).isEqualTo(0.2f);
    }
}