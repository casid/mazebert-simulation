package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.SellTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class SellTower extends Usecase<SellTowerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public void execute(SellTowerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        Tower tower = unitGateway.findUnit(Tower.class, command.playerId, command.x, command.y);
        if (tower == null) {
            return;
        }

        Item[] items = tower.removeAllItems();
        for (Item item : items) {
            if (item != null) {
                wizard.itemStash.add(ItemType.forItem(item));
            }
        }

        wizard.addGold(StrictMath.round(Balancing.GOLD_RETURN_WHEN_TOWER_SOLD * tower.getGoldCost()));

        unitGateway.removeUnit(tower);
    }
}
