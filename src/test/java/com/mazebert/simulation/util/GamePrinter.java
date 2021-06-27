package com.mazebert.simulation.util;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationValidator;
import com.mazebert.simulation.commands.*;
import com.mazebert.simulation.replay.StreamReplayReader;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class GamePrinter {
    @Test
    void print() throws Exception {
        int version = Sim.v24;

        //printGame(version, "C:\\Users\\casid\\Downloads\\Android-crash-simplay-6f95118c-9a51-490b-bcef-d6467af42335-1624762869870\\6f95118c-9a51-490b-bcef-d6467af42335.mbg");
        //printGame(version, "C:\\Users\\casid\\Downloads\\Android-crash-simplay-6f95118c-9a51-490b-bcef-d6467af42335-1624762871056\\6f95118c-9a51-490b-bcef-d6467af42335.mbg");

        //printGame(version, "C:\\Users\\casid\\Downloads\\Android-crash-simplay-56898a5f-5759-4cd9-8214-6f96658d0222-1624680254568\\56898a5f-5759-4cd9-8214-6f96658d0222.mbg");
        printGame(version, "C:\\Users\\casid\\Downloads\\Android-crash-simplay-56898a5f-5759-4cd9-8214-6f96658d0222-1624680254580\\56898a5f-5759-4cd9-8214-6f96658d0222.mbg");
    }

    private void printGame(int version, String file) throws IOException {
        try (InputStream is = Files.newInputStream(Paths.get(file))) {
            try (StreamReplayReader replayReader = new StreamReplayReader(is, version)) {
                new SimulationValidator().validate(version, replayReader, context -> {
                    context.commandExecutor = new CommandExecutorWrapper(context.commandExecutor);
                }, null);
            }
        }
    }

    void printCommand(Command request) {
        if(request.internal) {
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("p").append(request.playerId).append(", t").append(request.turnNumber).append(": ").append(request.getClass().getSimpleName()).append("(");

        if (request instanceof BuildTowerCommand) {
            BuildTowerCommand c = (BuildTowerCommand) request;
            result.append(c.towerType).append(", x=").append(c.x).append(", y=").append(c.y);
        }

        if (request instanceof SellTowerCommand) {
            SellTowerCommand c = (SellTowerCommand) request;
            result.append("x=").append(c.x).append(", y=").append(c.y);
        }

        if (request instanceof EquipItemCommand) {
            EquipItemCommand c = (EquipItemCommand) request;
            result.append(c.itemType).append(", x=").append(c.towerX).append(", y=").append(c.towerY).append(", index=").append(c.inventoryIndex);
        }

        if (request instanceof DrinkPotionCommand) {
            DrinkPotionCommand c = (DrinkPotionCommand) request;
            result.append(c.potionType).append(", x=").append(c.towerX).append(", y=").append(c.towerY).append(", all=").append(c.all);
        }

        if (request instanceof TransmuteCardsCommand) {
            TransmuteCardsCommand c = (TransmuteCardsCommand) request;
            result.append(c.cardCategory).append(", ").append(c.cardType).append(", all=").append(c.all).append(", auto=").append(c.automatic).append(", keep=").append(c.amountToKeep);
        }

        result.append(')');

        System.out.println(result);
    }

    private class CommandExecutorWrapper extends CommandExecutor {
        private final CommandExecutor commandExecutor;

        CommandExecutorWrapper(CommandExecutor commandExecutor) {
            this.commandExecutor = commandExecutor;
        }

        @Override
        public <Request> void executeVoid(Request request) {
            commandExecutor.executeVoid(request);

            printCommand((Command)request);
        }

        @Override
        public void init() {
            commandExecutor.init();
        }

        @Override
        protected Object resolveUsecase(Object usecase) {
            return usecase;
        }
    }
}
