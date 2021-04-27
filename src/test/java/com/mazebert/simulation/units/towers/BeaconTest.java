package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BeaconTest extends SimTest {
    Wizard wizard;
    Beacon beacon;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        beacon = new Beacon();
        beacon.setWizard(wizard);
        unitGateway.addUnit(beacon);
    }

    @Test
    void levelCap() {
        assertThat(beacon.getMaxLevel()).isEqualTo(Balancing.MAX_TOWER_LEVEL_CAP);
    }

    @Test
    void levelCapExperience() {
        beacon.addExperience(Balancing.getTowerExperienceForLevel(120));
        assertThat(beacon.getLevel()).isEqualTo(120);
    }

    @Test
    void fromLevel99ToLevel100() {
        beacon.addExperience(Balancing.getTowerExperienceForLevel(99));
        beacon.addExperience(Balancing.getTowerExperienceForLevel(100) - beacon.getExperience() + 1);

        assertThat(beacon.getLevel()).isEqualTo(100);
        assertThat(wizard.potionStash.get(PotionType.LeuchtFeuer).amount).isEqualTo(1);
    }

    @Test
    void fromLevel99ToLevel110() {
        beacon.addExperience(Balancing.getTowerExperienceForLevel(99));
        beacon.addExperience(Balancing.getTowerExperienceForLevel(110) - beacon.getExperience() + 1);

        assertThat(beacon.getLevel()).isEqualTo(110);
        assertThat(wizard.potionStash.get(PotionType.LeuchtFeuer).amount).isEqualTo(11);
    }

}