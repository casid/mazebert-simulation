package com.mazebert.simulation.systems;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.plugins.FormatPlugin;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class ProphecySystem {
    private final Tower[] prophecyTowers;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public ProphecySystem() {
        prophecyTowers = new Tower[Sim.context().playerGateway.getPlayerCount()];
    }

    public boolean isProphecyFulfilled(Wizard wizard, ItemType prophecy) {
        int playerIndex = wizard.playerId - 1;

        Tower prophecyTower = prophecyTowers[playerIndex];
        if (prophecyTower == null) {
            return false;
        }

        Item item = prophecyTower.removeItem(prophecy);
        if (item == null) {
            return false;
        }

        if (simulationListeners.areNotificationsEnabled()) {
            FormatPlugin formatPlugin = Sim.context().formatPlugin;
            simulationListeners.showNotification(prophecyTower, formatPlugin.prophecyTitle("Prophecy fulfilled!"));
            simulationListeners.showNotification(prophecyTower.getWizard(), formatPlugin.prophecyTitle("Prophecy fulfilled: ") + formatPlugin.prophecyDescription(item.getDescription()));
        }

        return true;
    }

    public void setProphecyTower(int playerId, Tower prophecyTower) {
        prophecyTowers[playerId - 1] = prophecyTower;
    }
}
