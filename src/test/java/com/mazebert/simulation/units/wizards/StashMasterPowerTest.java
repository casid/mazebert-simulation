package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.towers.Manitou;
import com.mazebert.simulation.units.towers.Rabbit;
import com.mazebert.simulation.units.towers.Tower;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class StashMasterPowerTest extends SimTest {
    Wizard wizard;
    StashMasterPower power;
    Tower tower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        wizard.playerId = 1;
        power = new StashMasterPower();
        power.setSkillLevel(10);

        wizard.level = power.getRequiredLevel();
        wizard.addAbility(power);
        unitGateway.addUnit(wizard);

        tower = new Manitou();
    }

    @Test
    void towerCardIsAdded() {
        whenTowerIsAdded();
        assertThat(tower.getInventorySize()).isEqualTo(5);
    }

    @Test
    void skillLevelTooLowForTower() {
        power.setSkillLevel(5);
        whenTowerIsAdded();
        assertThat(tower.getInventorySize()).isEqualTo(4);
    }

    @Test
    void level1() {
        tower = new Rabbit();
        power.setSkillLevel(1);

        whenTowerIsAdded();

        assertThat(tower.getInventorySize()).isEqualTo(5);
    }

    @Test
    void level0() {
        tower = new Rabbit();
        power.setSkillLevel(0);

        whenTowerIsAdded();

        assertThat(tower.getInventorySize()).isEqualTo(4);
    }

    private void whenTowerIsAdded() {
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);
    }

}