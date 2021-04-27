package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.units.items.ItemTest;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Beacon_ReplacesExistingTower extends ItemTest {
    @BeforeEach
    void setUp() {
        wizard.addGold(10000); // enough to afford beacon
    }

    @Test
    void replaceHighXpTower() {
        tower.setExperience(Balancing.getTowerExperienceForLevel(110));

        Tower beacon = whenTowerIsReplaced(this.tower, TowerType.Beacon);

        assertThat(beacon.getLevel()).isEqualTo(110);
        assertThat(wizard.potionStash.get(PotionType.LeuchtFeuer).amount).isEqualTo(11);
    }

    @Test
    void replaceLowXpTower() {
        tower.setExperience(Balancing.getTowerExperienceForLevel(90));

        Tower beacon = whenTowerIsReplaced(this.tower, TowerType.Beacon);

        assertThat(beacon.getLevel()).isEqualTo(90);
        assertThat(wizard.potionStash.get(PotionType.LeuchtFeuer)).isNull();
    }
}
