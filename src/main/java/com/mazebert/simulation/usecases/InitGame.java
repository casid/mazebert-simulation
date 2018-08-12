package com.mazebert.simulation.usecases;

import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.countdown.GameCountDown;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import org.jusecase.inject.Component;

import javax.inject.Inject;

@Component
public strictfp class InitGame extends Usecase<InitGameCommand> {

    @Inject
    private RandomPlugin randomPlugin;

    @Override
    public void execute(InitGameCommand command) {
        randomPlugin.setSeed(command.randomSeed);

        new GameCountDown().start();
    }

}
