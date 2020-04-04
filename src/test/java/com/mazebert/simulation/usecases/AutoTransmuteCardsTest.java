package com.mazebert.simulation.usecases;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.AutoTransmuteCardsCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AutoTransmuteCardsTest extends UsecaseTest<AutoTransmuteCardsCommand> {
    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        usecase = new AutoTransmuteCards();
        request.playerId = 1;
    }

    @Test
    void tower() {
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Rabbit;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isTrue();
    }

    @Test
    void tower_remove() {
        wizard.towerStash.addAutoTransmute(TowerType.Rabbit);
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Rabbit;
        request.remove = true;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isFalse();
    }

    @Test
    void tower_keepOne() {
        request.cardCategory = CardCategory.Tower;
        request.cardType = TowerType.Rabbit;
        request.amountToKeep = 1;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isFalse();
        wizard.towerStash.add(TowerType.Rabbit);
        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isTrue();
    }
}