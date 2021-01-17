package com.mazebert.simulation.usecases;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.commands.SellTowerCommand;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class SellTower extends Usecase<SellTowerCommand> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final LootSystem lootSystem = Sim.context().lootSystem;

    @Override
    public void execute(SellTowerCommand command) {
        Wizard wizard = unitGateway.getWizard(command.playerId);
        if (wizard == null) {
            return;
        }

        Tower tower = unitGateway.findTower(command.playerId, command.x, command.y);
        if (tower == null) {
            return;
        }

        execute(wizard, tower);
    }

    public void execute(Wizard wizard, Tower tower) {
        tower.markForDisposal();

        unitGateway.returnAllItemsToInventory(wizard, tower);

        lootSystem.addGold(wizard, tower, getGoldForSelling(tower));

        tower.onTowerSold.dispatch();
        unitGateway.removeUnit(tower);
    }

    public static int getGoldForSelling(Tower tower) {
        return StrictMath.round(Balancing.GOLD_RETURN_WHEN_TOWER_SOLD * tower.getGoldCost());
    }
}
