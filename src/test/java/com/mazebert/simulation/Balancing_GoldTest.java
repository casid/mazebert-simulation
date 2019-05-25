package com.mazebert.simulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class Balancing_GoldTest {
    @Test
    void highRoundGold() {
        assertThat(Balancing.getGoldForRound(120000, Sim.v15)).isEqualTo(-1);
        assertThat(Balancing.getGoldForRound(120000, Sim.v16)).isEqualTo(1000);
    }
}