package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.SellTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.WoodenStaff;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class SellTowerTest extends UsecaseTest<SellTowerCommand> {
    Wizard wizard;
    Tower tower;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        lootSystem = new LootSystem();

        wizard = new Wizard();
        wizard.playerId = 1;
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        tower.setX(2);
        tower.setY(4);
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        usecase = new SellTower();

        request.playerId = wizard.getPlayerId();
        request.x = 2;
        request.y = 4;
    }

    @Test
    void towerDoesNotExist_nothingHappens() {
        request.x = 10;
        request.y = 13;

        whenRequestIsExecuted();
    }

    @Test
    void towerIsRemoved() {
        whenRequestIsExecuted();
        assertThat(unitGateway.hasUnit(tower)).isFalse();
    }

    @Test
    void goldIsReturned() {
        wizard.gold = 100;
        whenRequestIsExecuted();
        assertThat(wizard.gold).isEqualTo(140);
    }

    @Test
    void itemsAreReturned() {
        tower.setItem(0, new BabySword());
        tower.setItem(2, new BabySword());
        tower.setItem(3, new WoodenStaff());

        whenRequestIsExecuted();

        assertThat(wizard.itemStash.get(ItemType.BabySword).amount).isEqualTo(2);
        assertThat(wizard.itemStash.get(ItemType.WoodenStaff).amount).isEqualTo(1);
    }
}