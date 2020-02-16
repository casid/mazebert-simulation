package com.mazebert.simulation.changelog;

import com.mazebert.simulation.CardType;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.heroes.HeroType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangelogTest extends SimTest {
    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
    }

    @Test
    void towers() {
        assertChangelogExists(TowerType.values());
    }

    @Test
    void items() {
        assertChangelogExists(ItemType.values());
    }

    @Test
    void potions() {
        assertChangelogExists(PotionType.values());
    }

    @Test
    void heroes() {
        assertChangelogExists(HeroType.values());
    }

    private void assertChangelogExists(CardType<?>[] cardTypes) {
        for (CardType<?> type : cardTypes) {
            Changelog changelog = type.instance().getChangelog();
            assertThat(changelog).describedAs("Changelog of card " + type + " is null").isNotNull();
            assertThat(changelog.getEntries().length).describedAs("Changelog of card " + type + " is empty").isGreaterThan(0);
        }
    }
}