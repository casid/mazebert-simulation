package com.mazebert.simulation;

import com.mazebert.simulation.commands.*;
import com.mazebert.simulation.units.abilities.ActiveAbilityType;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public class SimTest extends Context {
    public SimTest() {
        Sim.setContext(this);
    }

    protected void whenItemIsEquipped(Tower tower, ItemType itemType) {
        whenItemIsEquipped(tower, itemType, 0);
    }

    protected void whenItemIsEquipped(Tower tower, ItemType itemType, int inventoryIndex) {
        Wizard wizard = tower.getWizard();
        if (itemType != null && !wizard.itemStash.contains(itemType)) {
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
        Wizard wizard = tower.getWizard();
        if (!wizard.potionStash.contains(potionType)) {
            wizard.potionStash.add(potionType);
        }

        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = potionType;
        command.playerId = wizard.getPlayerId();
        command.towerX = (int)tower.getX();
        command.towerY = (int)tower.getY();
        commandExecutor.executeVoid(command);
    }

    protected void whenAllPotionAreConsumed(Tower tower, PotionType potionType) {
        Wizard wizard = tower.getWizard();

        DrinkPotionCommand command = new DrinkPotionCommand();
        command.potionType = potionType;
        command.playerId = wizard.getPlayerId();
        command.towerX = (int)tower.getX();
        command.towerY = (int)tower.getY();
        command.all = true;
        commandExecutor.executeVoid(command);
    }

    protected Tower whenTowerIsBuilt(Wizard wizard, TowerType towerType, int x, int y) {
        if (!wizard.towerStash.contains(towerType)) {
            wizard.towerStash.add(towerType);
        }

        BuildTowerCommand command = new BuildTowerCommand();
        command.playerId = wizard.getPlayerId();
        command.towerType = towerType;
        command.x = x;
        command.y = y;
        commandExecutor.executeVoid(command);

        return unitGateway.findTower(wizard.getPlayerId(), x, y);
    }

    protected Tower whenTowerIsReplaced(Tower tower, TowerType towerType) {
        return whenTowerIsBuilt(tower.getWizard(), towerType, (int)tower.getX(), (int)tower.getY());
    }

    protected void whenTowerIsSold(Tower tower) {
        Wizard wizard = tower.getWizard();

        SellTowerCommand command = new SellTowerCommand();
        command.playerId = wizard.getPlayerId();
        command.x = (int)tower.getX();
        command.y = (int)tower.getY();
        commandExecutor.executeVoid(command);
    }

    @SuppressWarnings("SameParameterValue")
    protected Tower whenTowerNeighbourIsBuilt(Tower tower, TowerType towerType, int offsetX, int offsetY) {
        return whenTowerIsBuilt(tower.getWizard(), towerType, (int)tower.getX() + offsetX, (int)tower.getY() + offsetY);
    }

    protected void whenAllCardsAreTransmuted(Wizard wizard, ItemType itemType) {
        TransmuteCardsCommand command = new TransmuteCardsCommand();
        command.playerId = wizard.getPlayerId();
        command.cardCategory = CardCategory.Item;
        command.cardType = itemType;
        command.all = true;
        commandExecutor.executeVoid(command);
    }

    protected void whenCardIsTransmuted(Wizard wizard, ItemType itemType) {
        TransmuteCardsCommand command = new TransmuteCardsCommand();
        command.playerId = wizard.getPlayerId();
        command.cardCategory = CardCategory.Item;
        command.cardType = itemType;
        command.all = false;
        commandExecutor.executeVoid(command);
    }

    protected void whenTowerAttacks(Tower tower) {
        tower.simulate(tower.getCooldown());
    }

    protected void whenAbilityIsActivated(Tower tower, ActiveAbilityType abilityType) {
        Wizard wizard = tower.getWizard();

        ActivateAbilityCommand command = new ActivateAbilityCommand();
        command.playerId = wizard.getPlayerId();
        command.towerX = (int) tower.getX();
        command.towerY = (int) tower.getY();
        command.abilityType = abilityType;
        commandExecutor.executeVoid(command);
    }
}
