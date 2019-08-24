package com.mazebert.simulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Balancing_MaxElementsTest {
    @Test
    void maxElements() {
        // Used by client
        assertThat(Balancing.MAX_ELEMENTS).isEqualTo(2);
    }
}