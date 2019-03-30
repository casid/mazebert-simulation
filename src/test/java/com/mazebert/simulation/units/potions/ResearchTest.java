package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.units.items.ItemTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

class ResearchTest extends ItemTest {
    @BeforeEach
    void setUp() {
        wizard.towerStash.setElements(EnumSet.noneOf(Element.class));
    }

    @Test
    void darkness() {
        whenPotionIsConsumed(PotionType.ResearchDarkness);

        assertThat(wizard.towerStash.isElementResearched(Element.Darkness)).isTrue();
        assertThat(wizard.towerStash.isElementResearched(Element.Metropolis)).isFalse();
        assertThat(wizard.towerStash.isElementResearched(Element.Nature)).isFalse();
    }

    @Test
    void nature() {
        whenPotionIsConsumed(PotionType.ResearchNature);

        assertThat(wizard.towerStash.isElementResearched(Element.Darkness)).isFalse();
        assertThat(wizard.towerStash.isElementResearched(Element.Metropolis)).isFalse();
        assertThat(wizard.towerStash.isElementResearched(Element.Nature)).isTrue();
    }

    @Test
    void metropolis() {
        whenPotionIsConsumed(PotionType.ResearchMetropolis);

        assertThat(wizard.towerStash.isElementResearched(Element.Darkness)).isFalse();
        assertThat(wizard.towerStash.isElementResearched(Element.Metropolis)).isTrue();
        assertThat(wizard.towerStash.isElementResearched(Element.Nature)).isFalse();
    }

    @Test
    void drinkAll() {
        whenPotionIsConsumed(PotionType.ResearchDarkness);
        whenPotionIsConsumed(PotionType.ResearchNature);
        whenPotionIsConsumed(PotionType.ResearchMetropolis);

        assertThat(wizard.towerStash.isElementResearched(Element.Darkness)).isTrue();
        assertThat(wizard.towerStash.isElementResearched(Element.Metropolis)).isTrue();
        assertThat(wizard.towerStash.isElementResearched(Element.Nature)).isTrue();
    }
}