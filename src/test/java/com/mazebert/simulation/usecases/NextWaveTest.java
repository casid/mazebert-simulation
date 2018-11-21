package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.NextWaveCommand;
import com.mazebert.simulation.gateways.WaveGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NextWaveTest extends UsecaseTest<NextWaveCommand> {
    private boolean waveStarted;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        waveGateway = new WaveGateway();

        usecase = new NextWave();

        simulationListeners.onWaveStarted.add(() -> waveStarted = true);
    }

    @Test
    void start() {
        whenRequestIsExecuted();
        assertThat(waveStarted).isTrue();
    }
}