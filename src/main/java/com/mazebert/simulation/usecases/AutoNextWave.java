package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.AutoNextWaveCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class AutoNextWave implements Usecase<AutoNextWaveCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final FormatPlugin format = Sim.context().formatPlugin;

    @Override
    public void execute(AutoNextWaveCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        if (gameGateway.getGame().autoNextWave == command.autoNextWave) {
            return;
        }

        gameGateway.getGame().autoNextWave = command.autoNextWave;

        if (simulationListeners.areNotificationsEnabled()) {
            String on = command.autoNextWave ? "on" : "off";
            simulationListeners.showNotification(wizard, format.playerName(wizard) + " turned " + on + " auto next wave.");
        }

        simulationListeners.onAutoNextWave.dispatch(command.playerId, command.autoNextWave);
    }
}
