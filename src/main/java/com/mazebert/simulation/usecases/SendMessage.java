package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.SendMessageCommand;

public strictfp class SendMessage implements Usecase<SendMessageCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(SendMessageCommand command) {
        simulationListeners.onMessageReceived.dispatch(command.playerId, command.message);
    }
}
