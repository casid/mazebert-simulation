package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.towers.TowerType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class DeckMasterPowerTest extends SimTest {
    Wizard wizard;
    DeckMasterPower power;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        power = new DeckMasterPower();
        power.setSkillLevel(7);
        power.setSelectedTower(TowerType.Manitou);

        wizard.level = power.getRequiredLevel();

    }

    @Test
    void towerCardIsAdded() {
        whenAbilityIsAdded();
        assertThat(wizard.towerStash.get(TowerType.Manitou).amount).isEqualTo(1);
    }

    @Test
    void skillLevelTooLowForTower() {
        power.setSkillLevel(5);
        whenAbilityIsAdded();
        assertThat(wizard.towerStash.get(TowerType.Manitou)).isNull();
    }

    @Test
    void noCardSelected() {
        power.setSelectedTower(null);
        whenAbilityIsAdded();
        assertThat(wizard.towerStash.size()).isEqualTo(0);
    }

    @Test
    void wizardLevelTooLow() {
        wizard.level = 69;
        whenAbilityIsAdded();
        assertThat(wizard.towerStash.size()).isEqualTo(0);
    }

    private void whenAbilityIsAdded() {
        wizard.addAbility(power);
        unitGateway.addUnit(wizard);
    }

}