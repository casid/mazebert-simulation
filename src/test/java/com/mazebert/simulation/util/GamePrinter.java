package com.mazebert.simulation.util;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.Simulation;
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
        int version = 28;

        printGame(version, "C:\\Users\\casid\\Downloads\\98da3b34-44da-4424-a859-b7e5fac0b3e5-38931.mbg");
    }

    private void printGame(int version, String file) throws IOException {
        try (InputStream is = Files.newInputStream(Paths.get(file))) {
            try (StreamReplayReader replayReader = new StreamReplayReader(is, version)) {
                new SimulationValidator().validate(version, replayReader, context -> {
                    System.out.println("Season: " + context.season);
                    System.out.println("Sim Player ID: " + context.playerGateway.getSimulationPlayerId());
                    System.out.println("Player count: " + context.playerGateway.getPlayerCount());
                    context.commandExecutor = new CommandExecutorWrapper(context.commandExecutor);
                }, context -> {
                    Simulation simulation = context.simulation;
                    System.out.println("Validation complete, play time: " + simulation.getPlayTimeInSeconds() + "s");
                    System.out.println("Bonus round seconds: " + context.gameGateway.getGame().bonusRoundSeconds);
                    System.out.println("Experience: " + (context.unitGateway.getWizard(1).experience - context.unitGateway.getWizard(1).initialExperience));
                });
            }
        }
    }

    void printCommand(Command request) {
        if(request.internal) {
            return;
        }

        StringBuilder result = new StringBuilder();
        result.append("p").append(request.playerId).append(", t").append(request.turnNumber).append(": ").append(request.getClass().getSimpleName()).append("(");

        if (request instanceof InitPlayerCommand) {
            InitPlayerCommand c = (InitPlayerCommand) request;
            result.append(c.playerName).append(", id=").append(c.ladderPlayerId).append(", hero=").append(c.heroType);
        }

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

        if (request instanceof TransferCardCommand) {
            TransferCardCommand c = (TransferCardCommand) request;
            result.append(c.cardCategory).append(", ").append(c.cardType).append(", toPlayerId=").append(c.toPlayerId);
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
        public <C extends Command> void execute(C request) {
            commandExecutor.execute(request);

            printCommand(request);
        }

        @Override
        public void init() {
            commandExecutor.init();
        }
    }
}
