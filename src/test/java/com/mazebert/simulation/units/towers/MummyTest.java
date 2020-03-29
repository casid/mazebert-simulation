package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MummyTest extends ItemTest {
    @Override
    protected Tower createTower() {
        formatPlugin = new FormatPlugin();
        return new Mummy();
    }

    @Test
    void customBonus() {
        CustomTowerBonus customTowerBonus = new CustomTowerBonus();

        tower.populateCustomTowerBonus(customTowerBonus);

        assertThat(customTowerBonus.title).isEqualTo("Kill chance:");
        assertThat(customTowerBonus.value).isEqualTo("1%");
    }
}