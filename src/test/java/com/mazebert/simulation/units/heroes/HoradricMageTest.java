package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class HoradricMageTest extends SimTest {
    Wizard wizard;
    HoradricMage horadricMage;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.gold = 200;
        wizard.interestBonus = 0.02f;
        unitGateway.addUnit(wizard);

        horadricMage = new HoradricMage();
        horadricMage.setWizard(wizard);
    }

    @Test
    void regular() {
        assertThat(wizard.requiredTransmuteAmount).isEqualTo(4);
    }

    @Test
    void startBonus() {
        unitGateway.addUnit(horadricMage);
        assertThat(wizard.requiredTransmuteAmount).isEqualTo(3);
    }
}