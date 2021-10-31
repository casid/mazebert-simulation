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

    public boolean fulfillProphecy(ItemType prophecy) {
        for (Tower prophecyTower : prophecyTowers) {
            if (prophecyTower == null) {
                continue;
            }

            Wizard wizard = prophecyTower.getWizard();
            if (wizard == null) {
                continue;
            }

            boolean prophecyFulfilled = fulfillProphecy(wizard, prophecy);
            if (prophecyFulfilled) {
                return true;
            }
        }

        return false;
    }

    public boolean fulfillProphecy(Wizard wizard, ItemType prophecy) {
        Tower prophecyTower = getProphecyTower(wizard);
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
            simulationListeners.showNotification(prophecyTower.getWizard(), formatPlugin.prophecyTitle("Prophecy fulfilled: ") + formatPlugin.card(item.getType()));
        }

        wizard.onProphecyFulfilled.dispatch(wizard, item);

        return true;
    }

    public boolean isProphecyAvailable(Wizard wizard, ItemType prophecy) {
        Tower prophecyTower = getProphecyTower(wizard);
        if (prophecyTower == null) {
            return false;
        }

        return prophecyTower.hasItem(prophecy);
    }

    public Wizard getProphecyWizard(ItemType prophecy) {
        for (Tower prophecyTower : prophecyTowers) {
            if (prophecyTower == null) {
                continue;
            }

            if (prophecyTower.hasItem(prophecy)) {
                return prophecyTower.getWizard();
            }
        }

        return null;
    }

    public void setProphecyTower(int playerId, Tower prophecyTower) {
        prophecyTowers[playerId - 1] = prophecyTower;
    }

    private Tower getProphecyTower(Wizard wizard) {
        int playerIndex = wizard.playerId - 1;
        if (playerIndex < 0 || playerIndex >= prophecyTowers.length) {
            return null;
        }

        Tower prophecyTower = prophecyTowers[playerIndex];
        if (prophecyTower == null) {
            return null;
        }

        return prophecyTower;
    }
}
