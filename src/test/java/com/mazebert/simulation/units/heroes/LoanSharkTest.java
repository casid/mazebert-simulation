package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class LoanSharkTest extends SimTest {
    Wizard wizard;
    LoanShark loanShark;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.gold = 200;
        wizard.interestBonus = 0.02f;
        unitGateway.addUnit(wizard);

        loanShark = new LoanShark();
        loanShark.setWizard(wizard);
    }

    @Test
    void startAttributes() {
        unitGateway.addUnit(loanShark);

        assertThat(wizard.gold).isEqualTo(5200);
        assertThat(wizard.interestBonus).isEqualTo(0.01f);
    }

    @Test
    void towerDoesNotTriggerStartBonus() {
        unitGateway.addUnit(loanShark);
        unitGateway.addUnit(new TestTower());

        assertThat(wizard.gold).isEqualTo(5200);
        assertThat(wizard.interestBonus).isEqualTo(0.01f);
    }
}