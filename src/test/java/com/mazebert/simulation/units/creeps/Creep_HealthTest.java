package com.mazebert.simulation.units.creeps;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Creep_HealthTest {
    Creep creep = new Creep();

    @Test
    void death() {
        creep.setHealth(0.0);
        assertThat(creep.getHealth()).isEqualTo(0.0);
        assertThat(creep.getState()).isEqualTo(CreepState.Death);
    }

    @Test
    void death_oneMoreHit() {
        creep.setHealth(0.0);
        creep.setHealth(-10.0);
        assertThat(creep.getHealth()).isEqualTo(0.0);
        assertThat(creep.getState()).isEqualTo(CreepState.Death);
    }
}