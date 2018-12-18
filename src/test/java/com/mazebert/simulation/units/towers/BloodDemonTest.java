package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.BloodDemonBlade;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

strictfp class BloodDemonTest extends SimTest {
    Wizard wizard;
    BloodDemon bloodDemon;

    @BeforeEach
    void setUp() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        commandExecutor = new CommandExecutor();
        commandExecutor.init();
    }

    @Test
    void itemIsObtained() {
        whenBloodDemonIsBuilt();
        assertThat(bloodDemon.getItem(0)).isInstanceOf(BloodDemonBlade.class);
    }

    @Test
    void itemIsObtained_existingItemReturnsToStash() {
        Tower tower = new TestTower();
        tower.setItem(0, new BabySword());
        unitGateway.addUnit(tower);

        whenBloodDemonIsBuilt();

        assertThat(bloodDemon.getItem(0)).isInstanceOf(BloodDemonBlade.class);
        assertThat(wizard.itemStash.get(ItemType.BabySword).getAmount()).isEqualTo(1);
    }

    @Test
    void wizardHealthIsReduced() {
        whenBloodDemonIsBuilt();
        assertThat(wizard.health).isEqualTo(0.00999999f);
    }

    @Test
    void itemIncreasesBaseDamage() {
        whenBloodDemonIsBuilt();
        assertThat(bloodDemon.getAddedAbsoluteBaseDamage()).isEqualTo(99.0f);
    }

    @Test
    void baseDamageIncreaseIsBoundToItem() {
        whenBloodDemonIsBuilt();
        whenTowerIsBuilt(TowerType.Rabbit);

        Rabbit rabbit = unitGateway.findUnit(Rabbit.class, wizard.getPlayerId());
        assertThat(rabbit.getAddedAbsoluteBaseDamage()).isEqualTo(99.0f);
    }

    @Test
    void baseDamageIncreaseIsBoundToItem_dropped() {
        whenBloodDemonIsBuilt();

        whenItemIsDropped();
        assertThat(bloodDemon.getAddedAbsoluteBaseDamage()).isEqualTo(0.0f);

        whenItemIsEquipped(ItemType.BloodDemonBlade);
        assertThat(bloodDemon.getAddedAbsoluteBaseDamage()).isEqualTo(99.0f);
    }

    // TODO add test with inventory size changing item!

    private void whenBloodDemonIsBuilt() {
        whenTowerIsBuilt(TowerType.BloodDemon);
        this.bloodDemon = unitGateway.findUnit(BloodDemon.class, wizard.getPlayerId());
    }

    private void whenTowerIsBuilt(TowerType towerType) {
        wizard.towerStash.add(towerType);

        BuildTowerCommand command = new BuildTowerCommand();
        command.towerType = towerType;
        command.playerId = wizard.playerId;
        command.x = 0;
        command.y = 0;
        commandExecutor.executeVoid(command);
    }

    private void whenItemIsDropped() {
        whenItemIsEquipped(null);
    }

    private void whenItemIsEquipped(ItemType itemType) {
        EquipItemCommand command = new EquipItemCommand();
        command.playerId = wizard.getPlayerId();
        command.inventoryIndex = 0;
        command.itemType = itemType;
        command.towerX = 0;
        command.towerY = 0;
        commandExecutor.executeVoid(command);
    }
}