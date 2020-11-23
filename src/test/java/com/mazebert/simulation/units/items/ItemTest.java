package com.mazebert.simulation.units.items;

import com.mazebert.simulation.CommandExecutor;
import com.mazebert.simulation.SimTest;
import com.mazebert.simulation.SimulationListeners;
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
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.Collections;
import java.util.EnumSet;

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

    protected void givenAllUniqueItemsAlreadyDroppedExcept(ItemType ... exceptItemTypes) {
        EnumSet<ItemType> excludeSet = EnumSet.noneOf(ItemType.class);
        Collections.addAll(excludeSet, exceptItemTypes);

        for (ItemType itemType : ItemType.values()) {
            if (!excludeSet.contains(itemType)) {
                wizard.itemStash.setUnique(itemType, itemType.instance());
            }
        }
    }

    protected void whenItemIsEquipped(ItemType itemType) {
        whenItemIsEquipped(itemType, 0);
    }

    protected void whenItemIsEquipped(ItemType itemType, int inventoryIndex) {
        whenItemIsEquipped(tower, itemType, inventoryIndex);
    }

    protected void whenItemIsUnequipped() {
        whenItemIsUnequipped(0);
    }

    protected void whenItemIsUnequipped(int inventoryIndex) {
        whenItemIsEquipped(null, inventoryIndex);
    }

    protected void whenPotionIsConsumed(PotionType potionType) {
        whenPotionIsConsumed(tower, potionType);
    }

    protected void whenAllPotionAreConsumed(PotionType potionType) {
        whenAllPotionAreConsumed(tower, potionType);
    }

    protected void whenTowerIsSold() {
        whenTowerIsSold(tower);
    }

    protected void whenAllCardsAreTransmuted(ItemType itemType) {
        whenAllCardsAreTransmuted(wizard, itemType);
    }

    protected void whenCardIsTransmuted(ItemType itemType) {
        whenCardIsTransmuted(wizard, itemType);
    }

    protected void whenTowerAttacks() {
        whenTowerAttacks(tower);
    }
}
