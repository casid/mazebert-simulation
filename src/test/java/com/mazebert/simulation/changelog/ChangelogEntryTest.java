package com.mazebert.simulation.changelog;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ChangelogEntryTest {
    @Test
    void initial() {
        ChangelogEntry entry = new ChangelogEntry(10, false, 2015);
        assertThat(entry.isInitial()).isTrue();
    }

    @Test
    void notInitial() {
        ChangelogEntry entry = new ChangelogEntry(10, false, 2015, "bla");
        assertThat(entry.isInitial()).isFalse();
    }
}