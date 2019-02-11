package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.TurnGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapAura;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.items.*;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Dandelion;
import com.mazebert.simulation.units.towers.Hitman;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.StashMasterPower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuildTowerTest extends UsecaseTest<BuildTowerCommand> {
    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    TestMap map;
    Wizard wizard;
    Tower builtTower;

    public BuildTowerTest() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        lootSystem = new LootSystem();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        turnGateway = new TurnGateway(1);
        randomPlugin = randomPluginTrainer;

        map = new TestMap(2);
        gameGateway.getGame().map = map;
    }

    @BeforeEach
    void setUp() {
        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.towerStash.add(TowerType.Hitman);
        wizard.gold = 500;
        unitGateway.addUnit(wizard);

        usecase = new BuildTower();

        request.playerId = 1;
        request.towerType = TowerType.Hitman;
    }

    @Test
    void notEnoughGold() {
        wizard.gold = 0;
        whenRequestIsExecuted();
        assertThat(builtTower).isNull();
    }

    @Test
    void notBuildable() {
        map.givenNoTilesAreBuildable();
        whenRequestIsExecuted();
        assertThat(builtTower).isNull();
    }

    @Test
    void towerIsBuilt() {
        whenRequestIsExecuted();
        assertThat(builtTower).isInstanceOf(Hitman.class);
        assertThat(builtTower.getLevel()).isEqualTo(1);
        assertThat(wizard.towerStash.size()).isEqualTo(0);
    }

    @Test
    void towerIsBuilt_goldIsRemoved() {
        whenRequestIsExecuted();
        assertThat(wizard.gold).isEqualTo(435L);
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
    void wizardNotFound() {
        request.playerId = 42;
        whenRequestIsExecuted();
        assertThat(builtTower).isNull();
    }

    @Test
    void towerNotFound() {
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();
        assertThat(builtTower).isNull();
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
        builtTower.setBestHit(1000);
        builtTower.setTotalDamage(56000);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower.getKills()).isEqualTo(50);
        assertThat(builtTower.getExperience()).isEqualTo(300);
        assertThat(builtTower.getLevel()).isEqualTo(Balancing.getTowerLevelForExperience(300));
        assertThat(builtTower.getBestHit()).isEqualTo(0); // best hit is reset
        assertThat(builtTower.getTotalDamage()).isEqualTo(56000);
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
    void replace_potions_tears() {
        givenTowerIsAlreadyBuilt();
        givenPotionIsDrank(PotionType.Tears);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower.getMulticrit()).isEqualTo(2);
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

    @Test
    void replace_goldReturned() {
        givenTowerIsAlreadyBuilt();

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(wizard.gold).isEqualTo(432L);
    }

    @Test
    void replace_5_itemSlots() {
        StashMasterPower stashMasterPower = new StashMasterPower();
        stashMasterPower.setSkillLevel(stashMasterPower.getMaxSkillLevel());
        wizard.level = stashMasterPower.getRequiredLevel();
        wizard.addAbility(stashMasterPower);

        givenTowerIsAlreadyBuilt();
        givenItemIsEquipped(ItemType.BabySword, 0);
        givenItemIsEquipped(ItemType.WoodenStaff, 1);
        givenItemIsEquipped(ItemType.MeatMallet, 2);
        givenItemIsEquipped(ItemType.MonsterTeeth, 3);
        givenItemIsEquipped(ItemType.LeatherBoots, 4);

        wizard.towerStash.add(TowerType.Dandelion);
        request.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();

        assertThat(builtTower.getItem(0).getType()).isEqualTo(ItemType.BabySword);
        assertThat(builtTower.getItem(1).getType()).isEqualTo(ItemType.WoodenStaff);
        assertThat(builtTower.getItem(2).getType()).isEqualTo(ItemType.MeatMallet);
        assertThat(builtTower.getItem(3).getType()).isEqualTo(ItemType.MonsterTeeth);
        assertThat(builtTower.getItem(4).getType()).isEqualTo(ItemType.LeatherBoots);
    }

    @Test
    void aura_IncreasedRange() {
        map.givenAllTilesHaveAura(MapAura.IncreasedRange);
        whenRequestIsExecuted();
        assertThat(builtTower.getRange()).isEqualTo(7.0f);
    }

    @Test
    void aura_DecreasedRange() {
        map.givenAllTilesHaveAura(MapAura.DecreasedRange);
        whenRequestIsExecuted();
        assertThat(builtTower.getRange()).isEqualTo(5.0f);
    }

    @Test
    void goldenGrounds_cardNotOwned() {
        map.givenMapType(MapType.GoldenGrounds);
        whenRequestIsExecuted();
        assertThat(builtTower).isNull();
    }

    @Test
    void goldenGrounds_cardOwned() {
        map.givenMapType(MapType.GoldenGrounds);
        wizard.foilTowers.add(request.towerType);
        whenRequestIsExecuted();
        assertThat(builtTower).isNotNull();
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