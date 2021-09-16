package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HelTest extends ItemTest {

    @Override
    protected Tower createTower() {
        return new Hel();
    }

    @Test
    void name() {
        // TODO!!!
    }

    @Test
    void reviveCreeps() {
        // TODO this could cause a leak?!
    }

    @Test
    void helmOfHades() {
        whenItemIsEquipped(ItemType.HelmOfHades);
        assertThat(tower.getItem(0).getName()).isEqualTo("Helm-heim");

        whenItemIsUnequipped();
        assertThat(wizard.itemStash.get(0).getCard().getName()).isEqualTo("Helm of Hades");
    }
}