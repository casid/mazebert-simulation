package com.mazebert.simulation.usecases;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Dandelion;
import com.mazebert.simulation.units.towers.Hitman;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuildTowerTest extends UsecaseTest<BuildTowerCommand> {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    Wizard wizard;
    Tower builtTower;
    boolean onErrorCalled;
    boolean onCompleteCalled;

    public BuildTowerTest() {
        unitGateway = new UnitGateway();
        simulationListeners = new SimulationListeners();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        turnGateway = new TurnGateway(1);
        randomPlugin = randomPluginTrainer;
    }

    @BeforeEach
    void setUp() {
        wizard = new Wizard();
        wizard.setPlayerId(1);
        wizard.towerStash.add(TowerType.Hitman);
        unitGateway.addUnit(wizard);

        usecase = new BuildTower();

        request.playerId = 1;
        request.towerType = TowerType.Hitman;
    }

    @Test
    void towerIsBuilt() {
        whenRequestIsExecuted();
        assertThat(builtTower).isInstanceOf(Hitman.class);
        assertThat(builtTower.getLevel()).isEqualTo(1);
        assertThat(wizard.towerStash.size()).isEqualTo(0);
    }

    @Test
    void towerIsBuilt_availableMoreThanOnce() {
        wizard.towerStash.add(TowerType.Hitman);
        wizard.towerStash.add(TowerType.Hitman);

        whenRequestIsExecuted();

        assertThat(wizard.towerStash.size()).isEqualTo(1);
        assertThat(wizard.towerStash.get(0).getAmount()).isEqualTo(2);
    }

    @Test
    void towerIsBuilt_localCompletionHandler() {
        request.onComplete = () -> onCompleteCalled = true;

        whenRequestIsExecuted();

        assertThat(onCompleteCalled).isTrue();
        assertThat(onErrorCalled).isFalse();
    }

    @Test
    void towerNotFound() {
        request.towerType = TowerType.Dandelion;

        whenRequestIsExecuted();

        assertThat(builtTower).isNull();
    }

    @Test
    void towerNotFound_localErrorHandler() {
        request.towerType = TowerType.Dandelion;
        request.onError = () -> onErrorCalled = true;

        whenRequestIsExecuted();

        assertThat(onErrorCalled).isTrue();
        assertThat(onCompleteCalled).isFalse();
    }

    @Test
    void replace() {
        givenTowerIsAlreadyBuilt();
        givenItemIsEquipped(ItemType.BabySword, 1);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower).isInstanceOf(Dandelion.class);
        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.itemStash.size()).isEqualTo(0);
        assertThat(builtTower.getItem(1)).isInstanceOf(BabySword.class);
    }

    @Test
    void replace_fullInventory() {
        givenTowerIsAlreadyBuilt();
        givenItemIsEquipped(ItemType.BabySword, 0);
        givenItemIsEquipped(ItemType.BabySword, 1);
        givenItemIsEquipped(ItemType.BabySword, 2);
        givenItemIsEquipped(ItemType.BabySword, 3);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower).isInstanceOf(Dandelion.class);
        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.itemStash.size()).isEqualTo(0);
        assertThat(builtTower.getItem(0)).isInstanceOf(BabySword.class);
        assertThat(builtTower.getItem(1)).isInstanceOf(BabySword.class);
        assertThat(builtTower.getItem(2)).isInstanceOf(BabySword.class);
        assertThat(builtTower.getItem(3)).isInstanceOf(BabySword.class);
    }

    private void givenTowerIsAlreadyBuilt() {
        whenRequestIsExecuted();
    }

    private void givenItemIsEquipped(ItemType itemType, int inventoryIndex) {
        wizard.itemStash.add(itemType);
        EquipItemCommand equipItem = new EquipItemCommand();
        equipItem.itemType = itemType;
        equipItem.inventoryIndex = inventoryIndex;
        equipItem.playerId = 1;
        commandExecutor.executeVoid(equipItem);
    }

    @Override
    protected void whenRequestIsExecuted() {
        super.whenRequestIsExecuted();
        builtTower = unitGateway.findUnit(Tower.class, request.playerId);
    }
}