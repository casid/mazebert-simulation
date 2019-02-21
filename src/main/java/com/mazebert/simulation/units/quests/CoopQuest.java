package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.PlayerGateway;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnGameWonListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class CoopQuest extends Quest implements OnGameWonListener {
    private final PlayerGateway playerGateway = Sim.context().playerGateway;
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public CoopQuest() {
        super(QuestReward.Big, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        if (playerGateway.getPlayerCount() > 1) {
            simulationListeners.onGameWon.add(this);
        }
    }

    @Override
    protected void dispose(Wizard unit) {
        simulationListeners.onGameWon.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onGameWon() {
        unitGateway.forEach(Wizard.class, w -> {
            if (w != getUnit()) {
                w.onQuestCompleted.dispatch(w, this);
            }
        });

        addAmount(1);
    }

    @Override
    public String getSinceVersion() {
        return "1.5.0";
    }

    @Override
    public String getTitle() {
        return "Friends with Benefits";
    }

    @Override
    public String getDescription() {
        return "Win a game with friends, all of you will be rewarded!";
    }
}
