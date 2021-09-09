package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.PauseCommand;

public strictfp class Pause implements Usecase<PauseCommand> {
    @Override
    public void execute(PauseCommand command) {
        Sim.context().simulation.setPause(command.playerId, command.pause);
    }
}
