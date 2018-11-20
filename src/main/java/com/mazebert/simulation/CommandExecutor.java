package com.mazebert.simulation;

import com.mazebert.simulation.usecases.BuildTower;
import com.mazebert.simulation.usecases.EquipItem;
import com.mazebert.simulation.usecases.InitGame;
import com.mazebert.simulation.usecases.NextWave;
import org.jusecase.executors.manual.ManualUsecaseExecutor;

public strictfp class CommandExecutor extends ManualUsecaseExecutor {
    public void init() {
        addUsecase(new InitGame());
        addUsecase(new BuildTower());
        addUsecase(new NextWave());
        addUsecase(new EquipItem());
    }
}
