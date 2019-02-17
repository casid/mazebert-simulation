package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.units.wizards.Wizard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class CoopQuestTest extends SimTest {

    PlayerGatewayTrainer playerGatewayTrainer = new PlayerGatewayTrainer();

    @BeforeEach
    void setUp() {
        playerGateway = playerGatewayTrainer;
        simulationListeners = new SimulationListeners();
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
        playerGatewayTrainer.givenPlayerCount(2);
        Wizard player1 = new Wizard();
        CoopQuest coopQuest = new CoopQuest();
        player1.addAbility(coopQuest);

        simulationListeners.onGameWon.dispatch();

        assertThat(coopQuest.isComplete()).isTrue();
    }
}