package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.maps.MapType;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class EquipItem implements Usecase<EquipItemCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;

    @Override
    public void execute(EquipItemCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);

        Tower tower = unitGateway.findTower(command.playerId, command.towerX, command.towerY);
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
        dropCurrentlyEquippedItem(command, wizard, tower);
        tower.setItem(command.inventoryIndex, null, true);
    }

    private void equip(EquipItemCommand command, Wizard wizard, Tower tower) {
        if (command.itemType.instance().isForbiddenToEquip(tower)) {
            return;
        }

        if (gameGateway.getMap().getType() == MapType.GoldenGrounds && !wizard.ownsFoilCard(command.itemType)) {
            return;
        }

        Item item = wizard.itemStash.remove(command.itemType);
        if (item != null) {
            dropCurrentlyEquippedItem(command, wizard, tower);
            tower.setItem(command.inventoryIndex, item, true);
        }
    }

    private void dropCurrentlyEquippedItem(EquipItemCommand command, Wizard wizard, Tower tower) {
        Item previousItem = tower.getItem(command.inventoryIndex);
        if (previousItem != null) {
            wizard.itemStash.add(previousItem.getType());
        }
    }
}
