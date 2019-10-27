package com.mazebert.simulation;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class Path_AddTest {
    @Test
    void empty() {
        Path p = new Path(new Path(), new Path());
        assertThat(p.size()).isEqualTo(0);
    }

    @Test
    void firstEmpty() {
        Path p = new Path(new Path(1, 2), new Path());
        assertThat(p.size()).isEqualTo(1);
        assertThat(p.getPoints()).containsExactly(1, 2);
    }

    @Test
    void secondEmpty() {
        Path p = new Path(new Path(), new Path(2, 3));
        assertThat(p.size()).isEqualTo(1);
        assertThat(p.getPoints()).containsExactly(2, 3);
    }

    @Test
    void both() {
        Path p = new Path(new Path(1, 2, 3, 4), new Path(5, 6));
        assertThat(p.size()).isEqualTo(3);
        assertThat(p.getPoints()).containsExactly(1, 2, 3, 4, 5, 6);
    }
}