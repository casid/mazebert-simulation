package com.mazebert.simulation;

import com.mazebert.simulation.commands.*;
import com.mazebert.simulation.usecases.*;

import java.util.HashMap;
import java.util.Map;

public strictfp class CommandExecutor {
    private final Map<Class<? extends Command>, Usecase<? extends Command>> usecases = new HashMap<>();

    public void init() {
        addUsecase(InitGameCommand.class, new InitGame());
        addUsecase(InitPlayerCommand.class, new InitPlayer());
        addUsecase(BuildTowerCommand.class, new BuildTower());
        addUsecase(SellTowerCommand.class, new SellTower());
        addUsecase(NextWaveCommand.class, new NextWave());
        addUsecase(EquipItemCommand.class, new EquipItem());
        addUsecase(DrinkPotionCommand.class, new DrinkPotion());
        addUsecase(PauseCommand.class, new Pause());
        addUsecase(ActivateAbilityCommand.class, new ActivateAbility());
        addUsecase(TransmuteCardsCommand.class, new TransmuteCards());
        addUsecase(SendMessageCommand.class, new SendMessage());
        addUsecase(TakeElementCardCommand.class, new TakeElementCard());
        addUsecase(AutoTransmuteCardsCommand.class, new AutoTransmuteCards());
        addUsecase(TransferCardCommand.class, new TransferCard());
        addUsecase(AutoNextWaveCommand.class, new AutoNextWave());
    }

    @SuppressWarnings("unchecked")
    public <C extends Command> void execute(C command) {
        Usecase<C> usecase = (Usecase<C>)usecases.get(command.getClass());
        usecase.execute(command);
    }

    private  <C extends Command> void addUsecase(Class<C> commandClass, Usecase<C> usecase) {
        usecases.put(commandClass, usecase);
    }

}
