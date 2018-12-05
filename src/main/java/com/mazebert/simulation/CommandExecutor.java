package com.mazebert.simulation;

import com.mazebert.simulation.usecases.*;
import org.jusecase.executors.manual.ManualUsecaseExecutor;

public strictfp class CommandExecutor extends ManualUsecaseExecutor {
    public void init() {
        addUsecase(new InitGame());
        addUsecase(new BuildTower());
        addUsecase(new NextWave());
        addUsecase(new EquipItem());
        addUsecase(new DrinkPotion());
        addUsecase(new Pause());
        addUsecase(new ActivateAbility());
    }
}
