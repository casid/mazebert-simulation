package com.mazebert.simulation.usecases;

import com.mazebert.simulation.commands.Command;
import org.jusecase.VoidUsecase;

public abstract strictfp class Usecase<C extends Command> implements VoidUsecase<C> {
    @Override
    public abstract void execute(C command);
}
