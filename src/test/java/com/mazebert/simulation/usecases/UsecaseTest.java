package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.commands.Command;

public abstract class UsecaseTest<C extends Command> extends SimTest {
    protected Usecase<C> usecase;
    protected C command = createCommand();

    protected abstract C createCommand();

    protected void whenRequestIsExecuted() {
        usecase.execute(command);
    }
}
