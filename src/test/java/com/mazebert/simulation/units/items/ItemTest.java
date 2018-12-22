package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.commands.DrinkPotionCommand;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.plugins.random.RandomPluginTrainer;
import com.mazebert.simulation.units.TestTower;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class ItemTest extends SimTest {
    protected RandomPluginTrainer randomPluginTrainer = new RandomPluginTrainer();

    protected Wizard wizard;
    protected Tower tower;

    public ItemTest() {
        simulationListeners = new SimulationListeners();
        randomPlugin = randomPluginTrainer;
        unitGateway = new UnitGateway();
        gameGateway = new GameGateway();

        wizard = new Wizard();
        unitGateway.addUnit(wizard);

        tower = createTower();
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

    protected void whenPotionIsConsumed(Tower tower, PotionType potionType) {
        wizard.potionStash.add(potionType);
        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = potionType;
        command.playerId = wizard.getPlayerId();
        command.towerX = (int)tower.getX();
        command.towerY = (int)tower.getY();
        commandExecutor.executeVoid(command);
    }
}
