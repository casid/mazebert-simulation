package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListenersTrainer;
import com.mazebert.simulation.commands.AutoNextWaveCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AutoNextWaveTest extends UsecaseTest<AutoNextWaveCommand> {

    SimulationListenersTrainer simulationListenersTrainer;
    Wizard wizard;

    @BeforeEach
    void setUp() {
        formatPlugin = new FormatPlugin();

        simulationListenersTrainer = new SimulationListenersTrainer();
        simulationListeners = simulationListenersTrainer;

        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        usecase = new AutoNextWave();
    }

    @Test
    void activate() {
        command.autoNextWave = true;

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().autoNextWave).isTrue();
        simulationListenersTrainer.thenNotificationsAre(wizard, "<c=#ffffff>Unknown Wizard</c> turned on auto next wave.");
    }

    @Test
    void deactivate() {
        gameGateway.getGame().autoNextWave = true;
        command.autoNextWave = false;

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().autoNextWave).isFalse();
        simulationListenersTrainer.thenNotificationsAre(wizard, "<c=#ffffff>Unknown Wizard</c> turned off auto next wave.");
    }

    @Test
    void activate_alreadyActive() {
        gameGateway.getGame().autoNextWave = true;
        command.autoNextWave = true;

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().autoNextWave).isTrue();
        simulationListenersTrainer.thenNoNotificationsWereShown(wizard);
    }

    @Test
    void deactivate_alreadyInactive() {
        gameGateway.getGame().autoNextWave = false;
        command.autoNextWave = false;

        whenRequestIsExecuted();

        assertThat(gameGateway.getGame().autoNextWave).isFalse();
        simulationListenersTrainer.thenNoNotificationsWereShown(wizard);
    }

    @Override
    protected AutoNextWaveCommand createCommand() {
        return new AutoNextWaveCommand();
    }
}
