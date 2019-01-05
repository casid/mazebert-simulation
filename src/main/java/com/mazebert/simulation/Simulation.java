package com.mazebert.simulation;

import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.commands.InitGameCommand;
import com.mazebert.simulation.commands.InitPlayerCommand;
import com.mazebert.simulation.gateways.*;
import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.HashHistory;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.plugins.SleepPlugin;
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
    private float unmodifiedTurnTimeInSeconds = turnTimeInSeconds;
    private float timeModifier = 1;
    private float timeDilation;
    private float playTimeInSeconds;
    private boolean pause;
    private Hash hash = new Hash();

    public Simulation() {
        Sim.context().init(this);
    }

    public void start(InitGameCommand initGameCommand, InitPlayerCommand initPlayerCommand) {
        if (replayWriterGateway.isWriteEnabled()) {
            ReplayHeader header = new ReplayHeader();
            header.playerId = playerGateway.getPlayerId();
            header.playerCount = playerGateway.getPlayerCount();
            replayWriterGateway.writeHeader(header);
        }

        scheduleInitGame(initGameCommand);
        scheduleInitPlayer(initPlayerCommand);
    }

    public void resume() {
        schedule(Collections.emptyList(), turnGateway.getCurrentTurnNumber());
        schedule(Collections.emptyList(), turnGateway.getCurrentTurnNumber() + 1);

        simulationListeners.onGameLoaded.dispatch();
    }

    private void scheduleInitGame(InitGameCommand initGameCommand) {
        List<Command> commands = new ArrayList<>();
        if (initGameCommand != null) {
            commands.add(initGameCommand);
        }
        schedule(commands, 0);
    }

    private void scheduleInitPlayer(InitPlayerCommand initPlayerCommand) {
        List<Command> commands = new ArrayList<>();
        commands.add(initPlayerCommand);
        schedule(commands, 1);
    }

    public void stop() {
        replayWriterGateway.close();
    }

    public void setPause(int playerId, boolean pause) {
        if (this.pause != pause) {
            this.pause = pause;
            simulationListeners.onPause.dispatch(playerId, pause);
        }
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
                if (i >= turnNumbersToSimulate - 2) { // only the last two frames need to be hashed
                    simulateTurn(Collections.emptyList());
                    hashHistory.add(hash.get());
                } else {
                    simulateTurn(Collections.emptyList(), false);
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

    public void adjustSpeed(float factor) {
        timeModifier *= factor;
        turnTimeInSeconds = unmodifiedTurnTimeInSeconds * timeModifier;
    }

    @SuppressWarnings("unused") // use by client
    public boolean isPause() {
        return pause;
    }

    @SuppressWarnings("unused") // Used by client
    public float getTimeModifier() {
        if (pause) {
            return 0;
        }
        return timeModifier;
    }

    public float getTimeDilation() {
        return timeDilation;
    }

    public float getPlayTimeInSeconds() {
        return playTimeInSeconds;
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
        long end = sleepPlugin.nanoTime();

        timeDilation = (float)turnTimeInNanos / (end - start);
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
        simulateTurn(playerTurns, true);
    }

    private void simulateTurn(List<Turn> playerTurns, boolean hash) {
        simulatePlayerTurns(playerTurns);
        if (!pause) {
            simulateUnits();
            if (hash) {
                hashGameState();
            }

            playTimeInSeconds += turnTimeInSeconds;
        }
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
