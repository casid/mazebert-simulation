package com.mazebert.simulation.plugins;

import static org.assertj.core.api.Assertions.assertThat;

public class SleepPluginTrainer extends SleepPlugin {
    private Long nanos;

    @Override
    public void sleep(long nanos) {
        this.nanos = nanos;
    }

    public void thenItSleeps(long nanos) {
        assertThat(this.nanos).isNotNull();
        assertThat(this.nanos).isEqualTo(nanos);
    }
}