package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.items.ItemType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class TimeMasterPowerTest extends SimTest {
    Wizard wizard;
    TimeMasterPower power;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        power = new TimeMasterPower();
        power.setSkillLevel(1);

        wizard.level = power.getRequiredLevel();

    }

    @Test
    void towerCardIsAdded() {
        whenAbilityIsAdded();
        assertThat(wizard.itemStash.get(ItemType.ScepterOfTime).amount).isEqualTo(1);
    }

    @Test
    void skillLevelTooLowForTower() {
        power.setSkillLevel(0);
        whenAbilityIsAdded();
        assertThat(wizard.itemStash.size()).isEqualTo(0);
    }

    private void whenAbilityIsAdded() {
        wizard.addAbility(power);
        unitGateway.addUnit(wizard);
    }

}