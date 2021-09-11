package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.items.DrinkingHorn;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LokiTest extends ItemTest {

    private Tower veleda;

    @Override
    protected Tower createTower() {
        return new Loki();
    }

    @BeforeEach
    void setUp() {
        wizard.addGold(20000);
        veleda = whenTowerIsBuilt(wizard, TowerType.Veleda, 1, 1);
    }

    @Test
    void prophecyTriggersTransmutation() {
        whenItemIsEquipped(veleda, ItemType.BuildTowerWithBloodProphecy);
        whenTowerIsBuilt(wizard, TowerType.Rabbit, 1, 0);

        assertThat(wizard.itemStash.transmutedRares).isEqualTo(1);
    }

    @Test
    void countsAsViking() {
        whenItemIsEquipped(tower, ItemType.DrinkingHorn, 1);

        assertThat(tower.getItem(1)).isInstanceOf(DrinkingHorn.class);
    }
}