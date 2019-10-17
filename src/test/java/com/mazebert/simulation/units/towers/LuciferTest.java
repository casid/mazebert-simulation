package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.items.Lightbringer;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LuciferTest extends SimTest {

    Lucifer lucifer;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        Wizard wizard = new Wizard();
        unitGateway.addUnit(wizard);

        lucifer = new Lucifer();
        lucifer.setWizard(wizard);
        unitGateway.addUnit(lucifer);
    }

    @Test
    void startsWithSword() {
        assertThat(lucifer.getItem(0)).isInstanceOf(Lightbringer.class);
    }
}