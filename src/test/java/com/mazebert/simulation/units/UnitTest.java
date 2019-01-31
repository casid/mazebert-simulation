package com.mazebert.simulation.units;

import com.mazebert.simulation.units.creeps.Creep;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UnitTest {
    @Test
    void rangeMeasuredInFields1() {
        Creep creep = new Creep();
        assertThat(creep.isInRange(0, 0, 1)).isTrue();
        assertThat(creep.isInRange(0, 1, 1)).isTrue();
        assertThat(creep.isInRange(1, 1, 1)).isTrue();
        assertThat(creep.isInRange(-1, -1, 1)).isTrue();
        assertThat(creep.isInRange(0, 2, 1)).isFalse();
        assertThat(creep.isInRange(0, 1.2f, 1)).isTrue();
        assertThat(creep.isInRange(0, 1.4999f, 1)).isTrue();
        assertThat(creep.isInRange(0, 1.6f, 1)).isFalse();
    }

    @Test
    void rangeMeasuredInFields2() {
        Creep creep = new Creep();
        creep.setX(0.4f);
        creep.setY(0.4f);
        assertThat(creep.isInRange(0, 0, 1)).isTrue();
        assertThat(creep.isInRange(0, 1, 1)).isTrue();
        assertThat(creep.isInRange(1, 1, 1)).isTrue();
        assertThat(creep.isInRange(-1, -1, 1)).isTrue();
        assertThat(creep.isInRange(0, 2, 1)).isFalse();
        assertThat(creep.isInRange(0, 1.2f, 1)).isTrue();
        assertThat(creep.isInRange(0, 1.4999f, 1)).isTrue();
        assertThat(creep.isInRange(0, 1.6f, 1)).isFalse();
    }
}