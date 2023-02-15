package com.mazebert.simulation.gateways;

import com.mazebert.simulation.commands.Command;

import java.util.List;

public strictfp interface LocalCommandGateway {

    void addCommand(Command command);

    List<Command> reset();
}
