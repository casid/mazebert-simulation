package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.gateways.WaveGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DagonTest extends SimTest {
    Wizard wizard;
    Dagon dagon;
    Tower tower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        waveGateway = new WaveGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        dagon = new Dagon();
        dagon.setWizard(wizard);
        unitGateway.addUnit(dagon);

        tower = new TestTower();
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);
    }

    @Test
    void bonuses() {
        assertThat(tower.getItemChance()).isEqualTo(1.3f);
        assertThat(waveGateway.getCultistChance()).isEqualTo(1.1f);
        assertThat(tower.getEldritchCardModifier()).isEqualTo(2.0f);
    }
}