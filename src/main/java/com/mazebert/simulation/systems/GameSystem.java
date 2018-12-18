package com.mazebert.simulation.systems;

import com.mazebert.simulation.Balancing;
import com.mazebert.simulation.Game;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.GameGateway;
import com.mazebert.simulation.gateways.PlayerGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnHealthChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.towers.TowerType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class GameSystem implements OnHealthChangedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final PlayerGateway playerGateway = Sim.context().playerGateway;
    private final GameGateway gameGateway = Sim.context().gameGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    public void addWizards() {
        for (int playerId = 1; playerId <= playerGateway.getPlayerCount(); ++playerId) {
            addWizard(playerId);
        }
    }

    private void addWizard(int playerId) {
        Wizard wizard = new Wizard();
        wizard.playerId = playerId;
        wizard.addGold(Balancing.STARTING_GOLD);
        for (TowerType towerType : TowerType.values()) {
            wizard.towerStash.add(towerType);
            wizard.towerStash.add(towerType);
            wizard.towerStash.add(towerType);
        }
        for (ItemType itemType : ItemType.values()) {
            if (itemType != ItemType.BloodDemonBlade) {
                wizard.itemStash.add(itemType);
            }
        }
        for (PotionType value : PotionType.values()) {
            wizard.potionStash.add(value);
        }

        wizard.onHealthChanged.add(this);

        unitGateway.addUnit(wizard);
    }

    @Override
    public void onHealthChanged(Unit unit, double oldHealth, double newHealth) {
        double delta = (newHealth - oldHealth) / playerGateway.getPlayerCount();
        addGameHealth((float)delta);
    }

    private void addGameHealth(float delta) {
        Game game = gameGateway.getGame();
        float oldHealth = game.health;
        game.health += delta;
        simulationListeners.onGameHealthChanged.dispatch(oldHealth, game.health);
    }
}