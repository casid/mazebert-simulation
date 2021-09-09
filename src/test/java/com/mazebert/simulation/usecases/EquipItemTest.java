package com.mazebert.simulation.usecases;

import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.WoodenStaff;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EquipItemTest extends UsecaseTest<EquipItemCommand> {
    TestMap map;
    Tower tower;
    Wizard wizard;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();

        usecase = new EquipItem();

        command.playerId = 1;
        command.itemType = ItemType.BabySword;
        command.towerX = 4;
        command.towerY = 6;
        command.inventoryIndex = 0;

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.itemStash.add(command.itemType);
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        tower.setWizard(wizard);
        tower.setX(4);
        tower.setY(6);
        unitGateway.addUnit(tower);

        gameGateway.getGame().map = map = new TestMap(1);
    }

    @Test
    void equip() {
        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isInstanceOf(BabySword.class);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.1f);
    }

    @Test
    void itemNotInStash() {
        wizard.itemStash.remove(command.itemType);
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void equipTwoItems() {
        wizard.itemStash.add(command.itemType);

        whenRequestIsExecuted();
        command.inventoryIndex = 1;
        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isInstanceOf(BabySword.class);
        assertThat(tower.getItem(1)).isInstanceOf(BabySword.class);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.2f);
    }

    @Test
    void indexTooSmall() {
        command.inventoryIndex = -1;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void indexOneTooBig() {
        command.inventoryIndex = tower.getInventorySize();
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void indexTooBig() {
        command.inventoryIndex = 100;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void towerNotFound() {
        command.towerX = 100;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void unequip() {
        whenRequestIsExecuted();
        command.itemType = null;

        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isNull();
        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void unequip_twoItems() {
        wizard.itemStash.add(command.itemType);

        whenRequestIsExecuted();
        command.inventoryIndex = 1;
        whenRequestIsExecuted();

        command.itemType = null;
        whenRequestIsExecuted();
        command.inventoryIndex = 0;
        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isNull();
        assertThat(tower.getItem(1)).isNull();
        assertThat(wizard.itemStash.get(0).amount).isEqualTo(2);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void unequip_itemNotPresent() {
        command.itemType = null;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void replace() {
        whenRequestIsExecuted();
        wizard.itemStash.add(ItemType.WoodenStaff);
        command.itemType = ItemType.WoodenStaff;

        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isInstanceOf(WoodenStaff.class);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.05f);
        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(wizard.itemStash.get(0).cardType).isEqualTo(ItemType.BabySword);
    }

    @Test
    void goldenGrounds_itemNotOwned() {
        map.givenMapType(MapType.GoldenGrounds);
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void goldenGrounds_itemOwned() {
        wizard.foilItems.add(command.itemType);
        map.givenMapType(MapType.GoldenGrounds);
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isInstanceOf(BabySword.class);
    }

    @Test
    void equip_TransmuteUniques() {
        command.itemType = ItemType.TransmuteUniques;
        wizard.itemStash.add(command.itemType);

        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isNull();
    }

    @Override
    protected EquipItemCommand createCommand() {
        return new EquipItemCommand();
    }
}