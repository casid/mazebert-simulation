package com.mazebert.simulation;

import com.mazebert.simulation.usecases.BuildTower;
import com.mazebert.simulation.usecases.InitGame;
import org.jusecase.executors.manual.ManualUsecaseExecutor;

public strictfp class CommandExecutor extends ManualUsecaseExecutor {
    public void init() {
        addUsecase(new InitGame());
        addUsecase(new BuildTower());
    }
}
