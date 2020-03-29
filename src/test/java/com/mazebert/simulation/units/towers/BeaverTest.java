package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeaverTest extends ItemTest {
    @Override
    protected Tower createTower() {
        formatPlugin = new FormatPlugin();
        return new Beaver();
    }

    @Test
    void bonus() {
        CustomTowerBonus customTowerBonus = new CustomTowerBonus();

        tower.populateCustomTowerBonus(customTowerBonus);

        assertThat(customTowerBonus.value).isEqualTo("10%");
    }

    @Test
    void bonus_levelUp() {
        tower.setLevel(10);
        CustomTowerBonus customTowerBonus = new CustomTowerBonus();

        tower.populateCustomTowerBonus(customTowerBonus);

        assertThat(customTowerBonus.value).isEqualTo("11%");
    }
}