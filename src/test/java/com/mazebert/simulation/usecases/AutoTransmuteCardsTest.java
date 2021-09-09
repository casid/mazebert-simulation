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
        command.playerId = 1;
    }

    @Test
    void tower() {
        command.cardCategory = CardCategory.Tower;
        command.cardType = TowerType.Rabbit;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isTrue();
    }

    @Test
    void tower_remove() {
        wizard.towerStash.addAutoTransmute(TowerType.Rabbit);
        command.cardCategory = CardCategory.Tower;
        command.cardType = TowerType.Rabbit;
        command.remove = true;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isFalse();
    }

    @Test
    void tower_keepOne() {
        command.cardCategory = CardCategory.Tower;
        command.cardType = TowerType.Rabbit;
        command.amountToKeep = 1;

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isFalse();
        wizard.towerStash.add(TowerType.Rabbit);
        assertThat(wizard.towerStash.isAutoTransmute(TowerType.Rabbit)).isTrue();
    }

    @Override
    protected AutoTransmuteCardsCommand createCommand() {
        return new AutoTransmuteCardsCommand();
    }
}