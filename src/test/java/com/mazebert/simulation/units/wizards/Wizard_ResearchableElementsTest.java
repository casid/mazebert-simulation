package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.units.potions.PotionType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;

import static org.assertj.core.api.Assertions.assertThat;

class Wizard_ResearchableElementsTest extends SimTest {
    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();

        wizard = new Wizard();
        wizard.towerStash.setElements(EnumSet.noneOf(Element.class));

        season = true;
    }

    @Test
    void all() {
        assertThat(wizard.getResearchableElements()).containsExactlyInAnyOrder(PotionType.ResearchDarkness, PotionType.ResearchNature, PotionType.ResearchMetropolis, PotionType.ResearchLight);
    }

    @Test
    void onlyOne() {
        wizard.towerStash.setElements(EnumSet.of(Element.Nature, Element.Darkness, Element.Light));
        assertThat(wizard.getResearchableElements()).containsExactlyInAnyOrder(PotionType.ResearchMetropolis);
    }

    @Test
    void alreadyDropped() {
        wizard.potionStash.add(PotionType.ResearchDarkness);
        assertThat(wizard.getResearchableElements()).containsExactlyInAnyOrder(PotionType.ResearchMetropolis, PotionType.ResearchNature, PotionType.ResearchLight);
    }
}