package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Path;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.creeps.Creep;
import org.assertj.core.data.Offset;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class ElvisTest extends SimTest {
    Elvis elvis;

    Creep creep;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        elvis = new Elvis();
        unitGateway.addUnit(elvis);

        creep = new Creep();
        creep.setPath(new Path(-2, 1, +2, 1));
        unitGateway.addUnit(creep);
    }

    @Test
    void creepRegularSpeed() {
        whenCreepMovesAlongPath(0.0f);
        assertThat(creep.getSpeedModifier()).isEqualTo(1.0f);
    }

    @Test
    void creepEntersRange() {
        whenCreepMovesAlongPath(1.0f);
        assertThat(creep.getSpeedModifier()).isEqualTo(0.5f);
    }

    @Test
    void creepRecovers() {
        whenCreepMovesAlongPath(1.0f);
        assertThat(creep.getX()).isEqualTo(-1.0f); // exactly enters range

        whenCreepMovesAlongPath(1.9999f);
        assertThat(creep.getX()).isCloseTo(0.0f, Offset.offset(0.0001f)); // was now only half as fast for 2 sec

        whenCreepMovesAlongPath(0.001f);
        assertThat(creep.getSpeedModifier()).isEqualTo(1.25f); // accelerates to escape

        whenCreepMovesAlongPath(0.8f);
        assertThat(creep.getSpeedModifier()).isEqualTo(1.0f); // leaves range, back to normal
    }

    private void whenCreepMovesAlongPath(float time) {
        creep.simulate(time);
        elvis.simulate(0.1f); // for aura update
    }
}