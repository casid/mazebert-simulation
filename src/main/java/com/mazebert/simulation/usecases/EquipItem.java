package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.EquipItemCommand;
import com.mazebert.simulation.gateways.UnitGateway;
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

        if (wizard.itemStash.remove(command.itemType)) {
            tower.setItem(command.inventoryIndex, command.itemType.create());
        }
    }
}
