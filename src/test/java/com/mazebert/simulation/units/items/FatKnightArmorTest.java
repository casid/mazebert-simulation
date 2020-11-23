package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.potions.PotionType;
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

    @Test
    void doesNotCrashWithYggdrasil() {
        wizard.gold = 20000; // Enough to build all those towers...
        tower.setElement(Element.Darkness);
        tower.setLevel(1);
        whenItemIsEquipped(ItemType.FatKnightArmor); // And Armor is equipped

        // Yggdrasil, remove all branches
        Tower yggdrasil = whenTowerIsBuilt(wizard, TowerType.Yggdrasil, 1, 0);
        whenItemIsEquipped(yggdrasil, null, 0);
        whenItemIsEquipped(yggdrasil, null, 1);
        whenItemIsEquipped(yggdrasil, null, 2);
        whenItemIsEquipped(yggdrasil, null, 3);

        // Let Fat Knight tower equip darkness branch
        whenItemIsEquipped(ItemType.BranchOfYggdrasilDarkness, 1);

        // Let Huli equip nature branch
        Tower huli = whenTowerIsBuilt(wizard, TowerType.Huli, 1, 1);
        whenItemIsEquipped(huli, ItemType.BranchOfYggdrasilNature, 4);

        whenPotionIsConsumed(yggdrasil, PotionType.Sacrifice);

        // Does not crash, and all three towers are sacrificed.
        // When the fat knight tower is destroyed, the branch is returned back to the inventory,
        // since this slot no longer exists.
        // We let the carrier reference leak and destroy all towers for a better user experience.
        assertThat(wizard.health).isEqualTo(1.03f);
    }
}