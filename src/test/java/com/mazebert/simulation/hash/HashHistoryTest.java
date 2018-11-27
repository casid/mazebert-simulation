package com.mazebert.simulation.hash;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class HashHistoryTest {
    HashHistory history = new HashHistory(2);

    @Test
    void oneElement() {
        history.add(12345);
        assertThat(history.getOldest()).isEqualTo(12345);
    }

    @Test
    void twoElements() {
        history.add(5);
        history.add(6);
        assertThat(history.getOldest()).isEqualTo(5);
    }

    @Test
    void threeElements() {
        history.add(5);
        history.add(6);
        history.add(7);
        assertThat(history.getOldest()).isEqualTo(6);
    }

    @Test
    void fiveElements() {
        history.add(5);
        history.add(6);
        history.add(7);
        history.add(8);
        history.add(9);
        assertThat(history.getOldest()).isEqualTo(8);
    }
}