package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.GameTurnGateway;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapAura;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.systems.ProphecySystem;
import com.mazebert.simulation.units.items.*;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.*;
import com.mazebert.simulation.units.wizards.StashMasterPower;
import com.mazebert.simulation.units.wizards.Wizard;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BuildTowerTest extends UsecaseTest<BuildTowerCommand> {

    private static final int INITIAL_GOLD = 10000;

    RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    TestMap map;
    Wizard wizard;
    Tower builtTower;

    public BuildTowerTest() {
        simulationListeners = new SimulationListeners();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        playerGateway = new PlayerGatewayTrainer();
        lootSystem = new LootSystem();
        prophecySystem = new ProphecySystem();
        commandExecutor = new CommandExecutor();
        commandExecutor.init();

        turnGateway = new GameTurnGateway(1);
        randomPlugin = randomPluginTrainer;

        map = new TestMap(2);
        gameGateway.getGame().map = map;
    }

    @BeforeEach
    void setUp() {
        wizard = new Wizard();
        wizard.playerId = 1;
        wizard.towerStash.add(TowerType.Hitman);
        wizard.gold = INITIAL_GOLD;
        unitGateway.addUnit(wizard);

        usecase = new BuildTower();

        command.playerId = 1;
        command.towerType = TowerType.Hitman;
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

        assertThat(wizard.gold).isEqualTo(INITIAL_GOLD - 65);
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
        command.playerId = 42;
        whenRequestIsExecuted();
        assertThat(builtTower).isNull();
    }

    @Test
    void towerNotFound() {
        command.towerType = TowerType.Dandelion;
        whenRequestIsExecuted();
        assertThat(builtTower).isNull();
    }

    @Test
    void replace() {
        givenTowerIsAlreadyBuilt();
        givenItemIsEquipped(ItemType.BabySword, 1);

        whenTowerIsBuilt(TowerType.Dandelion);

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

        whenTowerIsBuilt(TowerType.Dandelion);

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

        whenTowerIsBuilt(TowerType.Dandelion);

        assertThat(builtTower.getKills()).isEqualTo(50);
        assertThat(builtTower.getExperience()).isEqualTo(300);
        assertThat(builtTower.getLevel()).isEqualTo(Balancing.getTowerLevelForExperience(300, Balancing.MAX_TOWER_LEVEL_CAP));
        assertThat(builtTower.getBestHit()).isEqualTo(0); // best hit is reset
        assertThat(builtTower.getTotalDamage()).isEqualTo(56000);
    }

    @Test
    void replace_potions() {
        givenTowerIsAlreadyBuilt();
        givenPotionIsDrank(PotionType.CommonSpeed);
        givenPotionIsDrank(PotionType.CommonCrit);

        whenTowerIsBuilt(TowerType.Dandelion);

        assertThat(builtTower.getCritChance()).isEqualTo(0.0582f);
        assertThat(builtTower.getCritDamage()).isEqualTo(0.351f);
        assertThat(builtTower.getAttackSpeedAdd()).isEqualTo(0.0402f);
    }

    @Test
    void replace_potions_tears() {
        givenTowerIsAlreadyBuilt();
        givenPotionIsDrank(PotionType.Tears);

        whenTowerIsBuilt(TowerType.Dandelion);

        assertThat(builtTower.getMulticrit()).isEqualTo(2);
    }

    @Test
    void replace_potionStack() {
        givenTowerIsAlreadyBuilt();
        for (int i = 0; i < 10; ++i) {
            givenPotionIsDrank(PotionType.CommonCrit);
        }

        whenTowerIsBuilt(TowerType.Dandelion);

        assertThat(builtTower.getCritChance()).isEqualTo(0.132f);
        assertThat(builtTower.getCritDamage()).isEqualTo(1.2600001f);
    }

    @Test
    void replace_goldReturned() {
        givenTowerIsAlreadyBuilt();

        whenTowerIsBuilt(TowerType.Dandelion);

        assertThat(wizard.gold).isEqualTo(INITIAL_GOLD - 68);
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

        whenTowerIsBuilt(TowerType.Dandelion);

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
    void aura_DecreasedRange_BearHunter() {
        wizard.gold = 10000;
        map.givenSize(10);
        map.givenAllTilesAreWalkable();
        map.givenAllTilesHaveAura(MapAura.DecreasedRange);

        command.x = 5;
        command.y = 5;

        whenTowerIsBuilt(TowerType.BearHunter);

        assertThat(builtTower.getRange()).isEqualTo(1);
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
        wizard.foilTowers.add(command.towerType);
        whenRequestIsExecuted();
        assertThat(builtTower).isNotNull();
    }

    @Test
    void bloodDemon_bladeAlreadyGivenFromOtherPlayer() {
        wizard.gold = 100000;
        wizard.itemStash.setUnique(ItemType.BloodDemonBlade, new BloodDemonBlade());

        whenTowerIsBuilt(TowerType.BloodDemon);

        assertThat(builtTower).isNull();
    }

    @Test
    void buildOnLand_landTower() {
        map.givenAllTilesAreLand();

        whenTowerIsBuilt(TowerType.Huli);

        assertThat(builtTower).isInstanceOf(Huli.class);
    }

    @Test
    void buildOnLand_waterTower() {
        map.givenAllTilesAreLand();

        whenTowerIsBuilt(TowerType.Hydra);

        assertThat(builtTower).isNull();
    }

    @Test
    void buildOnWater_landTower() {
        map.givenAllTilesAreWater();

        whenTowerIsBuilt(TowerType.Huli);

        assertThat(builtTower).isNull();
    }

    @Test
    void buildOnWater_waterTower() {
        map.givenAllTilesAreWater();

        whenTowerIsBuilt(TowerType.Hydra);

        assertThat(builtTower).isInstanceOf(Hydra.class);
    }

    private void givenTowerIsAlreadyBuilt() {
        whenRequestIsExecuted();
    }

    private void givenItemIsEquipped(ItemType itemType, int inventoryIndex) {
        whenItemIsEquipped(builtTower, itemType, inventoryIndex);
    }

    private void givenPotionIsDrank(PotionType potionType) {
        whenPotionIsConsumed(builtTower, potionType);
    }

    @Override
    protected BuildTowerCommand createCommand() {
        return new BuildTowerCommand();
    }

    @Override
    protected void whenRequestIsExecuted() {
        super.whenRequestIsExecuted();
        builtTower = unitGateway.findUnit(Tower.class, command.playerId);
    }

    private void whenTowerIsBuilt(TowerType towerType) {
        wizard.towerStash.add(towerType);
        command.towerType = towerType;
        whenRequestIsExecuted();
    }
}