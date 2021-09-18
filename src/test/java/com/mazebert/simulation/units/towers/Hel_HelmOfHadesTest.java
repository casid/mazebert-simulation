package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Hel_HelmOfHadesTest extends ItemTest {
    @Override
    protected Tower createTower() {
        formatPlugin = new FormatPlugin();
        return new Hel();
    }

    @Test
    void helmOfHades() {
        whenItemIsEquipped(ItemType.HelmOfHades);
        assertThat(tower.getItem(0).getName()).isEqualTo("Helm of Helheim");

        whenItemIsUnequipped();
        assertThat(wizard.itemStash.get(0).getCard().getName()).isEqualTo("Helm of Hades");
    }
}
