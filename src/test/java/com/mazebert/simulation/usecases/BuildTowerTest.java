package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.items.*;
import com.mazebert.simulation.units.potions.PotionType;
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
        givenItemIsEquipped(ItemType.WoodenStaff, 1);
        givenItemIsEquipped(ItemType.MeatMallet, 2);
        givenItemIsEquipped(ItemType.MonsterTeeth, 3);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower).isInstanceOf(Dandelion.class);
        assertThat(wizard.towerStash.size()).isEqualTo(0);
        assertThat(wizard.itemStash.size()).isEqualTo(0);
        assertThat(builtTower.getItem(0)).isInstanceOf(BabySword.class);
        assertThat(builtTower.getItem(1)).isInstanceOf(WoodenStaff.class);
        assertThat(builtTower.getItem(2)).isInstanceOf(MeatMallet.class);
        assertThat(builtTower.getItem(3)).isInstanceOf(MonsterTeeth.class);
    }

    @Test
    void replace_stats() {
        givenTowerIsAlreadyBuilt();
        builtTower.setKills(50);
        builtTower.setExperience(300);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower.getKills()).isEqualTo(50);
        assertThat(builtTower.getExperience()).isEqualTo(300);
        assertThat(builtTower.getLevel()).isEqualTo(Balancing.getTowerLevelForExperience(300));
    }

    @Test
    void replace_potions() {
        givenTowerIsAlreadyBuilt();
        givenPotionIsDrank(PotionType.CommonSpeed);
        givenPotionIsDrank(PotionType.CommonCrit);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower.getCritChance()).isEqualTo(0.0582f);
        assertThat(builtTower.getCritDamage()).isEqualTo(0.351f);
        assertThat(builtTower.getAttackSpeedAdd()).isEqualTo(0.0402f);
    }

    @Test
    void replace_potionStack() {
        givenTowerIsAlreadyBuilt();
        for (int i = 0; i < 10; ++i) {
            givenPotionIsDrank(PotionType.CommonCrit);
        }

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower.getCritChance()).isEqualTo(0.132f);
        assertThat(builtTower.getCritDamage()).isEqualTo(1.2600001f);
    }

    private void givenTowerIsAlreadyBuilt() {
        whenRequestIsExecuted();
    }

    private void givenItemIsEquipped(ItemType itemType, int inventoryIndex) {
        wizard.itemStash.add(itemType);
        EquipItemCommand command = new EquipItemCommand();
        command.itemType = itemType;
        command.inventoryIndex = inventoryIndex;
        command.playerId = wizard.getPlayerId();
        commandExecutor.executeVoid(command);
    }

    private void givenPotionIsDrank(PotionType potionType) {
        wizard.potionStash.add(potionType);
        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = potionType;
        command.playerId = wizard.getPlayerId();
        commandExecutor.executeVoid(command);
    }

    @Override
    protected void whenRequestIsExecuted() {
        super.whenRequestIsExecuted();
        builtTower = unitGateway.findUnit(Tower.class, request.playerId);
    }
}