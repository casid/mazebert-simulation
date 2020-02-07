package com.mazebert.simulation.units.items;

import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class FatKnightArmorTest extends ItemTest {

    @BeforeEach
    void setUp() {
        gameGateway.getGame().map = new TestMap(4);
    }

    @Test
    void inventorySize() {
        whenItemIsEquipped(ItemType.FatKnightArmor);
        assertThat(tower.getInventorySize()).isEqualTo(5);
    }

    @Test
    void inventorySize_otherTower() {
        Tower rabbit = whenTowerNeighbourIsBuilt(tower, TowerType.Rabbit, 1, 0);

        whenItemIsEquipped(ItemType.FatKnightArmor);

        assertThat(rabbit.getInventorySize()).isEqualTo(5);
    }

    @Test
    void inventorySize_otherTower_itemDropped() {
        Tower rabbit = whenTowerNeighbourIsBuilt(tower, TowerType.Rabbit, 1, 0);

        whenItemIsEquipped(ItemType.FatKnightArmor);
        whenItemIsEquipped(null);

        assertThat(tower.getInventorySize()).isEqualTo(4);
        assertThat(rabbit.getInventorySize()).isEqualTo(4);
    }

    @Test
    void inventorySize_otherTower_outOfRange() {
        Tower rabbit = whenTowerNeighbourIsBuilt(tower, TowerType.Rabbit, 2, 0);

        whenItemIsEquipped(ItemType.FatKnightArmor);

        assertThat(rabbit.getInventorySize()).isEqualTo(4);
    }

    @Test
    void dupeIsNotPossible() {
        whenItemIsEquipped(ItemType.FatKnightArmor, 3);
        whenItemIsEquipped(null, 3);

        assertThat(wizard.itemStash.get(ItemType.FatKnightArmor).amount).isEqualTo(1);
    }

    @Test
    void doesNotCrashWithOtherAuras() {
        Tower guard = whenTowerNeighbourIsBuilt(this.tower, TowerType.Guard, 1, 1);
        whenTowerNeighbourIsBuilt(this.tower, TowerType.Rabbit, 0, 1);
        Tower beaver = whenTowerNeighbourIsBuilt(this.tower, TowerType.Beaver, 1, 0);

        whenItemIsEquipped(ItemType.FatKnightArmor);
        whenItemIsEquipped(beaver, ItemType.GuardLance, 4);
        whenItemIsEquipped(null);

        assertThat(guard.getInventorySize()).isEqualTo(4); // Inventory size was left at 5
        whenItemIsEquipped(ItemType.FatKnightArmor); // Re-equipping resulted in a crash
    }
}