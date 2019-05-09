package com.mazebert.simulation;

import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.Command;
import com.mazebert.simulation.gateways.GameTurnGateway;
import com.mazebert.simulation.messages.Turn;
import com.mazebert.simulation.messages.serializers.TurnSerializer;
import org.junit.jupiter.api.Test;

import static com.mazebert.simulation.messages.TurnBuilder.turn;
import static org.assertj.core.api.Assertions.assertThat;
import static org.jusecase.Builders.a;

public class Simulation_SinglePlayerTest extends SimulationTest {

    @Test
    void emptyTurn() {
        turnGateway.onLocalTurnReceived(a(turn()));

        simulation.process();

        Command executedCommand = commandExecutorTrainer.getLastCommand();
        assertThat(executedCommand).isNull();
    }

    @Test
    void buildTower() {
        BuildTowerCommand command = new BuildTowerCommand();
        turnGateway.onLocalTurnReceived(a(turn().withCommand(command)));

        simulation.process();

        Command executedCommand = commandExecutorTrainer.getLastCommand();
        assertThat(executedCommand).isSameAs(command);
        assertThat(executedCommand.playerId).isEqualTo(1);
        assertThat(executedCommand.turnNumber).isEqualTo(0);
    }

    @Test
    void turnIsIncremented() {
        turnGateway.onLocalTurnReceived(a(turn().withTurnNumber(0)));
        simulation.process();
        assertThat(turnGateway.getCurrentTurnNumber()).isEqualTo(1);

        turnGateway.onLocalTurnReceived(a(turn().withTurnNumber(1)));
        simulation.process();
        assertThat(turnGateway.getCurrentTurnNumber()).isEqualTo(2);
    }

    @Test
    void turnIsIncremented_maxInt() {
        ((GameTurnGateway)turnGateway).setCurrentTurnNumber(Integer.MAX_VALUE - 1);
        turnGateway.onLocalTurnReceived(a(turn().withTurnNumber(Integer.MAX_VALUE - 1)));
        simulation.process();
        assertThat(turnGateway.getCurrentTurnNumber()).isEqualTo(0);
    }

    @Test
    void playerCommandsAreScheduled() {
        turnGateway.onLocalTurnReceived(a(turn()));
        BuildTowerCommand command = new BuildTowerCommand();
        localCommandGateway.addCommand(command);

        simulation.process();

        Turn turn = ((GameTurnGateway)turnGateway).getTurn(2, 1);
        assertThat(turn.playerId).isEqualTo(1);
        assertThat(turn.turnNumber).isEqualTo(2);
        assertThat(turn.commands).containsExactly(command);
    }

    @Test
    void playerCommandsAreScheduled_notMoreThanMax() {
        turnGateway.onLocalTurnReceived(a(turn()));
        for (int i = 0; i < TurnSerializer.MAX_COMMANDS + 1; ++i) {
            localCommandGateway.addCommand(new BuildTowerCommand());
        }

        simulation.process();

        Turn turn = ((GameTurnGateway)turnGateway).getTurn(2, 1);
        assertThat(turn.playerId).isEqualTo(1);
        assertThat(turn.turnNumber).isEqualTo(2);
        assertThat(turn.commands).hasSize(TurnSerializer.MAX_COMMANDS);
    }
}