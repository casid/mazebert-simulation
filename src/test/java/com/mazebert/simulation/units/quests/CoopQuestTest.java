package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;

public class CoopQuestTest extends SimTest {

    PlayerGatewayTrainer playerGatewayTrainer = new PlayerGatewayTrainer();

    @BeforeEach
    void setUp() {
        playerGateway = playerGatewayTrainer;
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
    }

    @Test
    void oneWizard() {
        Wizard player1 = new Wizard();
        CoopQuest coopQuest = new CoopQuest();
        player1.addAbility(coopQuest);

        simulationListeners.onGameWon.dispatch();

        assertThat(coopQuest.isComplete()).isFalse();
    }

    @Test
    void twoWizards() {
        AtomicReference<Quest> player1CompletedQuest = new AtomicReference<>();
        AtomicReference<Quest> player2CompletedQuest = new AtomicReference<>();
        AtomicInteger player1CompleteQuestCount = new AtomicInteger();
        AtomicInteger player2CompleteQuestCount = new AtomicInteger();

        playerGatewayTrainer.givenPlayerCount(2);
        Wizard player1 = new Wizard();
        CoopQuest coopQuest = new CoopQuest();
        player1.addAbility(coopQuest);
        player1.onQuestCompleted.add((w, q) -> {
            player1CompletedQuest.set(q);
            player1CompleteQuestCount.incrementAndGet();
        });
        unitGateway.addUnit(player1);

        Wizard player2 = new Wizard();
        player2.onQuestCompleted.add((w, q) -> {
            player2CompletedQuest.set(q);
            player2CompleteQuestCount.incrementAndGet();
        });
        unitGateway.addUnit(player2);

        simulationListeners.onGameWon.dispatch();

        assertThat(coopQuest.isComplete()).isTrue();
        assertThat(player1CompletedQuest.get()).isSameAs(coopQuest);
        assertThat(player2CompletedQuest.get()).isSameAs(coopQuest);
        assertThat(player1CompleteQuestCount.get()).isEqualTo(1);
        assertThat(player2CompleteQuestCount.get()).isEqualTo(1);
    }
}