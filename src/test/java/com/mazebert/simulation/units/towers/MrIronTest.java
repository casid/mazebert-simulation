package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class MrIronTest extends SimTest {
    MrIron mrIron;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        mrIron = new MrIron();
        unitGateway.addUnit(mrIron);
    }

    @Test
    void construct() {

    }
}