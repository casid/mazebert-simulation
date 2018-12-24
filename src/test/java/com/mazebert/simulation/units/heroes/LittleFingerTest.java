package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class LittleFingerTest extends SimTest {
    Wizard wizard;
    LittleFinger littleFinger;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        littleFinger = new LittleFinger();
        littleFinger.setWizard(wizard);
        unitGateway.addUnit(littleFinger);
    }

    @Test
    void heroBonus() {
        Tower tower = new TestTower();
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        assertThat(tower.getItemChance()).isEqualTo(1.2f);
        assertThat(tower.getItemQuality()).isEqualTo(1.2f);
    }

    @Test
    void heroBonus_towerOfOtherWizard() {
        Wizard wizard2 = new Wizard();
        wizard2.playerId = 2;
        unitGateway.addUnit(wizard2);

        Tower tower = new TestTower();
        tower.setWizard(wizard2);
        unitGateway.addUnit(tower);

        assertThat(tower.getItemChance()).isEqualTo(1.0f);
        assertThat(tower.getItemQuality()).isEqualTo(1.0f);
    }
}