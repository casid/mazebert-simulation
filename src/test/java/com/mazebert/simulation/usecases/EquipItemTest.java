package com.mazebert.simulation.usecases;

import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class EquipItemTest extends UsecaseTest<EquipItemCommand> {
    Tower tower;
    Wizard wizard;

    @BeforeEach
    void setUp() {
        unitGateway = new UnitGateway();

        usecase = new EquipItem();

        request.playerId = 1;
        request.itemType = ItemType.BabySword;
        request.towerX = 4;
        request.towerY = 6;
        request.inventoryIndex = 0;

        wizard = new Wizard();
        wizard.setPlayerId(1);
        wizard.itemStash.add(request.itemType);
        unitGateway.addUnit(wizard);

        tower = new TestTower();
        tower.setPlayerId(1);
        tower.setX(4);
        tower.setY(6);
        unitGateway.addUnit(tower);
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
}