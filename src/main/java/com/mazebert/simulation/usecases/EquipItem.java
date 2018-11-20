package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class EquipItem extends Usecase<EquipItemCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(EquipItemCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        Tower tower = unitGateway.findUnit(Tower.class, command.playerId, command.towerX, command.towerY);
        if (tower == null) {
            return;
        }

        if (command.inventoryIndex < 0 || command.inventoryIndex >= tower.getInventorySize()) {
            return;
        }

        if (command.itemType == null) {
            unequip(command, wizard, tower);
        } else {
            equip(command, wizard, tower);
        }
    }

    private void unequip(EquipItemCommand command, Wizard wizard, Tower tower) {
        Item previousItem = tower.getItem(command.inventoryIndex);
        if (previousItem != null) {
            tower.setItem(command.inventoryIndex, null);
            wizard.itemStash.add(ItemType.forItem(previousItem));
        }
    }

    private void equip(EquipItemCommand command, Wizard wizard, Tower tower) {
        if (wizard.itemStash.remove(command.itemType)) {
            tower.setItem(command.inventoryIndex, command.itemType.create());
        }
    }
}
