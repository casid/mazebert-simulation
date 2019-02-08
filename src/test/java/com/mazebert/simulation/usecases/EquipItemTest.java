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

        request.playerId = 1;
        request.itemType = ItemType.BabySword;
        request.towerX = 4;
        request.towerY = 6;
        request.inventoryIndex = 0;

        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.itemStash.add(request.itemType);
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
        wizard.itemStash.remove(request.itemType);
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void equipTwoItems() {
        wizard.itemStash.add(request.itemType);

        whenRequestIsExecuted();
        request.inventoryIndex = 1;
        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isInstanceOf(BabySword.class);
        assertThat(tower.getItem(1)).isInstanceOf(BabySword.class);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.2f);
    }

    @Test
    void indexTooSmall() {
        request.inventoryIndex = -1;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void indexOneTooBig() {
        request.inventoryIndex = tower.getInventorySize();
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void indexTooBig() {
        request.inventoryIndex = 100;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void towerNotFound() {
        request.towerX = 100;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void unequip() {
        whenRequestIsExecuted();
        request.itemType = null;

        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isNull();
        assertThat(wizard.itemStash.get(0).amount).isEqualTo(1);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void unequip_twoItems() {
        wizard.itemStash.add(request.itemType);

        whenRequestIsExecuted();
        request.inventoryIndex = 1;
        whenRequestIsExecuted();

        request.itemType = null;
        whenRequestIsExecuted();
        request.inventoryIndex = 0;
        whenRequestIsExecuted();

        assertThat(tower.getItem(0)).isNull();
        assertThat(tower.getItem(1)).isNull();
        assertThat(wizard.itemStash.get(0).amount).isEqualTo(2);
        assertThat(tower.getAddedRelativeBaseDamage()).isEqualTo(0.0f);
    }

    @Test
    void unequip_itemNotPresent() {
        request.itemType = null;
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isNull();
    }

    @Test
    void replace() {
        whenRequestIsExecuted();
        wizard.itemStash.add(ItemType.WoodenStaff);
        request.itemType = ItemType.WoodenStaff;

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
        wizard.foilItems.add(request.itemType);
        map.givenMapType(MapType.GoldenGrounds);
        whenRequestIsExecuted();
        assertThat(tower.getItem(0)).isInstanceOf(BabySword.class);
    }
}