package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.Lightbringer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LuciferTest extends ItemTest {

    Lucifer lucifer;

    @Override
    protected Tower createTower() {
        return lucifer = new Lucifer();
    }

    @Test
    void startsWithSword() {
        assertThat(lucifer.getItem(0)).isInstanceOf(Lightbringer.class);
    }

    @Test
    void swordRemoved_luciferIsReplacedWithDarkLucifer() {
        whenItemIsEquipped(null);

        assertThat(unitGateway.hasUnit(lucifer)).isFalse();
        assertThat(getLuciferFallen()).isNotNull();
    }

    @Test
    void swordRemoved_remainingItemsAreKept() {
        whenItemIsEquipped(ItemType.BabySword, 1);

        whenItemIsEquipped(null);

        assertThat(getLuciferFallen().getItem(1)).isInstanceOf(BabySword.class);
    }

    @Test
    void swordRemoved_lightbringerIsReplacedByDarkSwords() {
        whenItemIsEquipped(null);

        assertThat(wizard.itemStash.get(ItemType.Lightbringer)).isNull();
    }

    @Test
    void luciferIsSold() {
        whenTowerIsSold();

        assertThat(unitGateway.hasUnit(lucifer)).isFalse();
        assertThat(getLuciferFallen()).isNull();
        assertThat(wizard.itemStash.get(ItemType.Lightbringer)).isNull();
    }

    @Test
    void luciferIsReplaced() {
        whenTowerIsReplaced(lucifer, TowerType.Dandelion);

        assertThat(unitGateway.hasUnit(lucifer)).isFalse();
        assertThat(getLuciferFallen()).isNull();
        assertThat(wizard.itemStash.get(ItemType.Lightbringer)).isNull();
    }

    private LuciferFallen getLuciferFallen() {
        return unitGateway.findUnit(LuciferFallen.class, 1);
    }
}