package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.SendMessageCommand;

public class SendMessage extends Usecase<SendMessageCommand> {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    public void execute(SendMessageCommand command) {
        simulationListeners.onMessageReceived.dispatch(command.playerId, command.message);
    }
}
