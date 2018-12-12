package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class BlackWidowTest extends SimTest {
    BlackWidow blackWidow;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        blackWidow = new BlackWidow();
        unitGateway.addUnit(blackWidow);
    }

    @Test
    void creepIsModifiedInRange() {
        Creep creep = new Creep();
        unitGateway.addUnit(creep);

        assertThat(creep.getDropChance()).isEqualTo(1.2f);
        assertThat(creep.getGoldModifier()).isEqualTo(1.5f);

        creep.setX(4);
        blackWidow.simulate(0.1f);

        assertThat(creep.getDropChance()).isEqualTo(1.0f);
        assertThat(creep.getGoldModifier()).isEqualTo(1.0f);
    }
}