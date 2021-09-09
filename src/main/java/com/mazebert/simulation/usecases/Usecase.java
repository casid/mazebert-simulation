package com.mazebert.simulation.usecases;

import com.mazebert.simulation.commands.Command;

public strictfp interface Usecase<C extends Command> {
    void execute(C command);
}
