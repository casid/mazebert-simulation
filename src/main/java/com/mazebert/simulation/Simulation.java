package com.mazebert.simulation;

import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.units.Unit;
import org.jusecase.inject.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Component
public strictfp class Simulation {

    @Inject
    private CommandExecutor commandExecutor;
    @Inject
    private LocalCommandGateway localCommandGateway;
    @Inject
    private TurnGateway turnGateway;
    @Inject
    private SleepPlugin sleepPlugin;
    @Inject
    private MessageGateway messageGateway;
    @Inject
    private PlayerGateway playerGateway;
    @Inject
    private UnitGateway unitGateway;

    private boolean allPlayersReady;
    private long turnTimeInMillis = 100;
    private long turnTimeInNanos = TimeUnit.MILLISECONDS.toNanos(turnTimeInMillis);
    private float turnTimeInSeconds = turnTimeInMillis / 1000.0f;
    private Hash hash = new Hash();


    public void start() {
        commandExecutor.init();

        List<Command> requests = new ArrayList<>();
        if (playerGateway.isHost()) {
            InitGameCommand initGameCommand = new InitGameCommand();
            initGameCommand.randomSeed = new Random().nextLong();
            requests.add(initGameCommand);
        }
        schedule(requests, 0);

        schedule(Collections.emptyList(), 1);
    }

    public void process() {
        List<Turn> playerTurns = turnGateway.waitForAllPlayerTurns(messageGateway);
        simulate(playerTurns);

        List<Command> commands = localCommandGateway.reset();
        schedule(commands, turnGateway.getTurnNumberForLocalCommands());

        turnGateway.incrementTurnNumber();
    }

    private void simulate(List<Turn> playerTurns) {
        long start = System.nanoTime();

        if (!allPlayersReady) {
            System.out.println("All players ready, game simulation starts now");
            allPlayersReady = true;
        }

        checkHashes(playerTurns);

        simulateTurn(playerTurns);

        long duration = System.nanoTime() - start;
        sleepPlugin.sleep(Math.max(0, turnTimeInNanos - duration));
    }

    private void checkHashes(List<Turn> playerTurns) {
        int myHash = 0;
        for (Turn playerTurn : playerTurns) {
            if (playerTurn.playerId == playerGateway.getPlayerId()) {
                myHash = playerTurn.hash;
            }
        }

        for (Turn playerTurn : playerTurns) {
            if (playerTurn.hash != myHash) {
                // TODO handle it!! This means game over.
                System.err.println("Oh shit, we have a dsync with player " + playerTurn.playerId + ". Mine: " + myHash + ", theirs: " + playerTurn.hash);
            }
        }
    }

    private void simulateTurn(List<Turn> playerTurns) {
        simulatePlayerTurns(playerTurns);

        for (Unit unit : unitGateway.getUnits()) {
            unit.simulate(turnTimeInSeconds);
        }

        hash.reset();
        for (Unit unit : unitGateway.getUnits()) {
            unit.hash(hash);
        }
    }

    private void simulatePlayerTurns(List<Turn> playerTurns) {
        for (Turn playerTurn : playerTurns) {
            for (Command command : playerTurn.commands) {
                command.playerId = playerTurn.playerId;
                command.turnNumber = playerTurn.turnNumber;
                commandExecutor.executeVoid(command);
            }
        }
    }

    private void schedule(List<Command> commands, int turnNumber) {
        Turn turn = new Turn();
        turn.playerId = playerGateway.getPlayerId();
        turn.turnNumber = turnNumber;
        turn.commands = commands;
        turn.hash = hash.get();

        turnGateway.onLocalTurnReceived(turn);
        messageGateway.sendToOtherPlayers(turn);
    }
}
