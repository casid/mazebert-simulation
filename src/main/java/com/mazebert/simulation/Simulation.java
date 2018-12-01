package com.mazebert.simulation;

import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.HashHistory;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.plugins.SleepPlugin;
import com.mazebert.simulation.plugins.random.UuidRandomPlugin;
import com.mazebert.simulation.projectiles.ProjectileGateway;
import com.mazebert.simulation.replay.ReplayReader;
import com.mazebert.simulation.replay.data.ReplayFrame;
import com.mazebert.simulation.replay.data.ReplayHeader;
import com.mazebert.simulation.units.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public strictfp final class Simulation {

    private final CommandExecutor commandExecutor = Sim.context().commandExecutor;
    private final LocalCommandGateway localCommandGateway = Sim.context().localCommandGateway;
    private final TurnGateway turnGateway = Sim.context().turnGateway;
    private final SleepPlugin sleepPlugin = Sim.context().sleepPlugin;
    private final MessageGateway messageGateway = Sim.context().messageGateway;
    private final PlayerGateway playerGateway = Sim.context().playerGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final ProjectileGateway projectileGateway = Sim.context().projectileGateway;
    private final ReplayWriterGateway replayWriterGateway = Sim.context().replayWriterGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final SimulationMonitor monitor = Sim.context().simulationMonitor;

    private long turnTimeInMillis = 100;
    private long turnTimeInNanos = TimeUnit.MILLISECONDS.toNanos(turnTimeInMillis);
    private float turnTimeInSeconds = turnTimeInMillis / 1000.0f;
    private Hash hash = new Hash();

    public Simulation() {
        commandExecutor.init();
    }

    public void start() {
        if (gameGateway.getGame().id == null) {

            if (replayWriterGateway.isWriteEnabled()) {
                ReplayHeader header = new ReplayHeader();
                header.playerId = playerGateway.getPlayerId();
                header.playerCount = playerGateway.getPlayerCount();
                replayWriterGateway.writeHeader(header);
            }

            List<Command> commands = new ArrayList<>();
            if (playerGateway.isHost()) {
                InitGameCommand initGameCommand = new InitGameCommand();
                initGameCommand.gameId = UuidRandomPlugin.createSeed();
                initGameCommand.rounds = 250;
                commands.add(initGameCommand);
            }
            schedule(commands, 0);

            schedule(Collections.emptyList(), 1);
        } else {
            schedule(Collections.emptyList(), turnGateway.getCurrentTurnNumber());
            schedule(Collections.emptyList(), turnGateway.getCurrentTurnNumber() + 1);

            simulationListeners.onGameInitialized.dispatch();
        }
    }

    public void stop() {
        replayWriterGateway.close();
    }

    public void load(ReplayReader replayReader) {
        HashHistory hashHistory = new HashHistory(2);
        hashHistory.add(0);
        hashHistory.add(0);

        while (true) {
            ReplayFrame replayFrame = replayReader.readFrame();
            if (replayFrame == null) {
                return;
            }

            int turnNumbersToSimulate = replayFrame.turnNumber - turnGateway.getCurrentTurnNumber();
            for (int i = 0; i < turnNumbersToSimulate; ++i) {
                simulateTurn(Collections.emptyList());
                if (i >= turnNumbersToSimulate - 2) { // only the last two frames need to be hashed
                    hashHistory.add(hash.get());
                }
                turnGateway.incrementTurnNumber();
            }

            List<Turn> playerTurns = replayFrame.getTurns();
            simulateTurn(playerTurns);
            checkHashes(playerTurns, hashHistory.getOldest(), replayFrame.turnNumber);
            hashHistory.add(hash.get());
            turnGateway.incrementTurnNumber();
        }
    }

    public void process() {
        List<Turn> playerTurns = turnGateway.waitForAllPlayerTurns(messageGateway);
        simulate(playerTurns);

        List<Command> commands = localCommandGateway.reset();
        schedule(commands, turnGateway.getTurnNumberForLocalCommands());

        turnGateway.incrementTurnNumber();
    }

    public float getPlayTimeInSeconds() {
        return turnGateway.getCurrentTurnNumber() * turnTimeInSeconds;
    }

    private void simulate(List<Turn> playerTurns) {
        long start = sleepPlugin.nanoTime();

        checkHashes(playerTurns);

        if (monitor.isRequired()) {
            synchronized (monitor.get()) {
                simulateTurn(playerTurns);
            }
        } else {
            simulateTurn(playerTurns);
        }

        if (replayWriterGateway.isWriteEnabled()) {
            replayWriterGateway.writeTurn(turnGateway.getCurrentTurnNumber(), playerTurns);
        }

        sleepPlugin.sleepUntil(start, turnTimeInNanos);
    }

    private void checkHashes(List<Turn> playerTurns) {
        int myHash = 0;
        int myTurn = 0;
        for (Turn playerTurn : playerTurns) {
            if (playerTurn.playerId == playerGateway.getPlayerId()) {
                myHash = playerTurn.hash;
                myTurn = playerTurn.turnNumber;
            }
        }

        checkHashes(playerTurns, myHash, myTurn);
    }

    private void checkHashes(List<Turn> playerTurns, int expected, int myTurn) {
        for (Turn playerTurn : playerTurns) {
            if (playerTurn.hash != expected) {
                throw new DsyncException("Oh shit, we have a dsync with player " + playerTurn.playerId + ". Mine: " + expected + ", theirs: " + playerTurn.hash + "(My turn: " + myTurn + ", theirs: " + playerTurn.turnNumber);
            }
        }
    }

    private void simulateTurn(List<Turn> playerTurns) {
        simulatePlayerTurns(playerTurns);
        simulateUnits();
        hashGameState();
    }

    private void simulateUnits() {
        unitGateway.forEach(this::simulateUnit);
        projectileGateway.simulate(turnTimeInSeconds);
        simulationListeners.onUpdate.dispatch(turnTimeInSeconds);
    }

    private void simulateUnit(Unit unit) {
        unit.simulate(turnTimeInSeconds);
    }

    private void hashGameState() {
        hash.reset();
        hash.add(gameGateway.getGame());
        unitGateway.forEach(this::hashUnit);
    }

    private void hashUnit(Unit unit) {
        unit.hash(hash);
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
