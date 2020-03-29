package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FrogTest extends ItemTest {

    @Override
    protected Tower createTower() {
        formatPlugin = new FormatPlugin();
        return new Frog();
    }

    @Test
    void bonus() {
        CustomTowerBonus customTowerBonus = new CustomTowerBonus();

        tower.populateCustomTowerBonus(customTowerBonus);

        assertThat(customTowerBonus.value).isEqualTo("100%");
    }

    @Test
    void bonus_levelUp() {
        tower.setLevel(10);
        CustomTowerBonus customTowerBonus = new CustomTowerBonus();

        tower.populateCustomTowerBonus(customTowerBonus);

        assertThat(customTowerBonus.value).isEqualTo("120%");
    }
}