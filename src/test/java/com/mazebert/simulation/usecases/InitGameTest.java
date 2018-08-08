package com.mazebert.simulation.usecases;

import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.jusecase.inject.Trainer;

class InitGameTest extends UsecaseTest<InitGameCommand> {

    @Trainer
    RandomPluginTrainer randomPluginTrainer;

    @BeforeEach
    void setUp() {
        usecase = new InitGame();
    }

    @Test
    void name() {
        request.randomSeed = 1234;
        whenRequestIsExecuted();
        randomPluginTrainer.thenSeedIsSetTo(1234);
    }
}