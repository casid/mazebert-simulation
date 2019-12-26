package com.mazebert.simulation.units.towers;


import com.mazebert.simulation.units.creeps.CreepBuilder;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

strictfp class Unicorn_KilledWhenReplacedTest extends ItemTest {

    @BeforeEach
    void setUp() {
        wizard.gold = 100000;
    }

    /**
     * This is a pretty odd case.
     * Unicorn is built and instantly killed by a creep in range.
     */
    @Test
    void testItemsReturnToInventory() {
        randomPluginTrainer.givenFloatAbs(0.0f);
        unitGateway.addUnit(a(creep()));
        whenItemIsEquipped(ItemType.BabySword, 1);
        whenItemIsEquipped(ItemType.BabySword, 2);
        whenItemIsEquipped(ItemType.BabySword, 3);

        whenTowerIsReplaced(tower, TowerType.Unicorn);

        assertThat(wizard.itemStash.get(ItemType.BabySword).amount).isEqualTo(3);
        assertThat(wizard.potionStash.get(PotionType.UnicornTears).amount).isEqualTo(1);
    }

    CreepBuilder creep() {
        return CreepBuilder.creep().withWizard(wizard);
    }
}