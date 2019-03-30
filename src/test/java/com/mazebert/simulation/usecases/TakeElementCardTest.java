package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.TakeElementCardCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TakeElementCardTest extends UsecaseTest<TakeElementCardCommand> {

    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        usecase = new TakeElementCard();

        wizard = new Wizard();
        wizard.elementResearchPoints = 1;
        unitGateway.addUnit(wizard);

        request.card = PotionType.ResearchDarkness;
    }

    @Test
    void noCard_nothingHappens() {
        request.card = null;
        whenRequestIsExecuted();
        thenNoCardIsAdded();
    }

    @Test
    void wrongCard_nothingHappens() {
        request.card = PotionType.CommonSpeed;
        whenRequestIsExecuted();
        thenNoCardIsAdded();
    }

    @Test
    void noResearchPoints() {
        wizard.elementResearchPoints = 0;
        whenRequestIsExecuted();
        thenNoCardIsAdded();
    }

    @Test
    void success() {
        whenRequestIsExecuted();

        assertThat(wizard.potionStash.get(0).cardType).isEqualTo(request.card);
        assertThat(wizard.elementResearchPoints).isEqualTo(0);
    }

    private void thenNoCardIsAdded() {
        assertThat(wizard.potionStash.size()).isEqualTo(0);
    }
}