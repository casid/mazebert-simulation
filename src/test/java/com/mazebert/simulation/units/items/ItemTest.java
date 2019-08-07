package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.BuildTowerCommand;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.commands.TransmuteCardsCommand;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.PlayerGatewayTrainer;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.TestMap;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.systems.DamageSystemTrainer;
import com.mazebert.simulation.systems.ExperienceSystem;
import com.mazebert.simulation.systems.GameSystem;
import com.mazebert.simulation.systems.LootSystemTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class ItemTest extends SimTest {
    protected RandomPluginTrainer randomPluginTrainer;
    protected DamageSystemTrainer damageSystemTrainer;

    protected Wizard wizard;
    protected Tower tower;

    public ItemTest() {
        simulationListeners = new SimulationListeners();
        randomPlugin = randomPluginTrainer = new RandomPluginTrainer();
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();
        difficultyGateway = new DifficultyGateway();
        playerGateway = new PlayerGatewayTrainer();
        damageSystem = damageSystemTrainer = new DamageSystemTrainer();
        lootSystem = new LootSystemTrainer();
        experienceSystem = new ExperienceSystem();
        gameSystem = new GameSystem();

        gameGateway.getGame().map = new TestMap(2);

        wizard = new Wizard();
        wizard.playerId = 1;
        gameSystem.addWizard(wizard);

        tower = createTower();
        tower.setWizard(wizard);
        unitGateway.addUnit(tower);

        commandExecutor = new CommandExecutor();
        commandExecutor.init();
    }

    protected Tower createTower() {
        return new TestTower();
    }

    protected void whenItemIsEquipped(ItemType itemType) {
        whenItemIsEquipped(itemType, 0);
    }

    protected void whenItemIsEquipped(ItemType itemType, int inventoryIndex) {
        whenItemIsEquipped(tower, itemType, inventoryIndex);
    }

    protected void whenItemIsEquipped(Tower tower, ItemType itemType, int inventoryIndex) {
        if (itemType != null) {
            wizard.itemStash.add(itemType);
        }
        EquipItemCommand command = new EquipItemCommand();
        command.itemType = itemType;
        command.inventoryIndex = inventoryIndex;
        command.playerId = wizard.getPlayerId();
        command.towerX = (int)tower.getX();
        command.towerY = (int)tower.getY();
        commandExecutor.executeVoid(command);
    }

    protected void whenPotionIsConsumed(PotionType potionType) {
        whenPotionIsConsumed(tower, potionType);
    }

    protected void whenPotionIsConsumed(Tower tower, PotionType potionType) {
        wizard.potionStash.add(potionType);
        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = potionType;
        command.playerId = wizard.getPlayerId();
        command.towerX = (int)tower.getX();
        command.towerY = (int)tower.getY();
        commandExecutor.executeVoid(command);
    }

    protected void whenAllPotionAreConsumed(PotionType potionType) {
        whenAllPotionAreConsumed(tower, potionType);
    }

    protected void whenAllPotionAreConsumed(Tower tower, PotionType potionType) {
        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = potionType;
        command.playerId = wizard.getPlayerId();
        command.towerX = (int)tower.getX();
        command.towerY = (int)tower.getY();
        command.all = true;
        commandExecutor.executeVoid(command);
    }

    protected Tower whenTowerIsReplaced(Tower tower, TowerType towerType) {
        wizard.towerStash.add(towerType);
        BuildTowerCommand command = new BuildTowerCommand();
        command.playerId = wizard.getPlayerId();
        command.towerType = towerType;
        command.x = (int)tower.getX();
        command.y = (int)tower.getY();
        commandExecutor.executeVoid(command);

        return unitGateway.findTower(wizard.getPlayerId(), command.x, command.y);
    }

    @SuppressWarnings("SameParameterValue")
    protected Tower whenTowerNeighbourIsBuilt(Tower tower, TowerType towerType, int offsetX, int offsetY) {
        wizard.towerStash.add(towerType);
        BuildTowerCommand command = new BuildTowerCommand();
        command.playerId = wizard.getPlayerId();
        command.towerType = towerType;
        command.x = (int)tower.getX() + offsetX;
        command.y = (int)tower.getY() + offsetY;
        commandExecutor.executeVoid(command);

        return unitGateway.findTower(wizard.getPlayerId(), command.x, command.y);
    }

    protected void whenAllCardsAreTransmuted(ItemType itemType) {
        TransmuteCardsCommand command = new TransmuteCardsCommand();
        command.playerId = wizard.getPlayerId();
        command.cardCategory = CardCategory.Item;
        command.cardType = itemType;
        command.all = true;
        commandExecutor.executeVoid(command);
    }

    protected void whenCardIsTransmuted(ItemType itemType) {
        TransmuteCardsCommand command = new TransmuteCardsCommand();
        command.playerId = wizard.getPlayerId();
        command.cardCategory = CardCategory.Item;
        command.cardType = itemType;
        command.all = false;
        commandExecutor.executeVoid(command);
    }

    protected void whenTowerAttacks() {
        tower.simulate(tower.getCooldown());
    }
}
