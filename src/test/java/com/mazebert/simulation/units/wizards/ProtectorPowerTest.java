package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public strictfp class ProtectorPowerTest extends SimTest {
    Wizard wizard;
    ProtectorPower power;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        power = new ProtectorPower();
        power.setSkillLevel(10);

        wizard.level = power.getRequiredLevel();
    }

    @Test
    void healthIsIncreased() {
        whenAbilityIsAdded();
        assertThat(wizard.health).isEqualTo(2.0f);
    }

    @Test
    void healthIsIncreased_dependingOnInvest() {
        power.setSkillLevel(5);
        whenAbilityIsAdded();
        assertThat(wizard.health).isEqualTo(1.5f);
    }

    private void whenAbilityIsAdded() {
        wizard.addAbility(power);
        unitGateway.addUnit(wizard);
    }
}