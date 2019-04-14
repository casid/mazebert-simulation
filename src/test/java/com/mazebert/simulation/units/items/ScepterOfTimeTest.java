package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Simulation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class ScepterOfTimeTest extends ItemTest {

    @BeforeEach
    void setUp() {
        simulation = new Simulation(false);
    }

    @Test
    void timeModifier_equipped() {
        whenItemIsEquipped(ItemType.ScepterOfTime);
        assertThat(simulation.getTimeModifier()).isEqualTo(2);
    }

    @Test
    void timeModifier_dropped() {
        whenItemIsEquipped(ItemType.ScepterOfTime);
        whenItemIsEquipped(null);

        assertThat(simulation.getTimeModifier()).isEqualTo(1);
    }

    @Test
    void twoSceptersCanBeUsed() {
        whenItemIsEquipped(ItemType.ScepterOfTime, 0);
        whenItemIsEquipped(ItemType.ScepterOfTime, 1);

        assertThat(simulation.getTimeModifier()).isEqualTo(4);
    }

    @Test
    void twoSceptersCanBeUsed_dropped() {
        whenItemIsEquipped(ItemType.ScepterOfTime, 0);
        whenItemIsEquipped(ItemType.ScepterOfTime, 1);
        whenItemIsEquipped(null, 1);

        assertThat(simulation.getTimeModifier()).isEqualTo(2);
    }
}