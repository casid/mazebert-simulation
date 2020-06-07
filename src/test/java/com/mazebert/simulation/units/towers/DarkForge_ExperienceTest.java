package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.DarkBabySword;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class DarkForge_ExperienceTest extends SimTest {
    Wizard wizard;
    DarkForge darkForge;
    Tower tower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        experienceSystem = new ExperienceSystem();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        darkForge = new DarkForge();
        darkForge.setWizard(wizard);
        unitGateway.addUnit(darkForge);

        tower = new TestTower();
        unitGateway.addUnit(tower);
    }

    @Test
    void darkItemsGrantExperience() {
        tower.setItem(0, new DarkBabySword());
        experienceSystem.grantExperience(tower, 10);

        assertThat(darkForge.getExperience()).isEqualTo(1.5f);
        assertThat(tower.getExperience()).isEqualTo(8.5f);
    }


    @Test
    void darkItemsGrantExperience_multipleTimes() {
        tower.setItem(0, new DarkBabySword());
        tower.setItem(1, new DarkBabySword());
        experienceSystem.grantExperience(tower, 10);

        assertThat(darkForge.getExperience()).isEqualTo(3.0f);
        assertThat(tower.getExperience()).isEqualTo(7.0f);
    }

    @Test
    void mrIronDoesNotLooseXp() {
        MrIron mrIron = new MrIron();
        mrIron.setExperience(10000);
        unitGateway.addUnit(mrIron);
        givenDarkItemsAreIntegrated(mrIron);
        givenDarkItemsAreIntegrated(mrIron);
        givenDarkItemsAreIntegrated(mrIron);

        experienceSystem.grantExperience(mrIron, 10);
        assertThat(mrIron.getExperience()).isEqualTo(10000);
        assertThat(darkForge.getExperience()).isEqualTo(10);
    }

    private void givenDarkItemsAreIntegrated(MrIron mrIron) {
        mrIron.setItem(0, new DarkBabySword());
        mrIron.setItem(1, new DarkBabySword());
        mrIron.setItem(2, new DarkBabySword());
        mrIron.setItem(3, new DarkBabySword());
        mrIron.getAbility(MrIronConstruct.class).activate();
    }
}