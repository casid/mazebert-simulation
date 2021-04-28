package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.DifficultyGateway;
import com.mazebert.simulation.listeners.OnGameWonListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class HellVictoryQuest extends Quest implements OnGameWonListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final DifficultyGateway difficultyGateway = Sim.context().difficultyGateway;

    public HellVictoryQuest() {
        super(QuestReward.Bigger, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        simulationListeners.onGameWon.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        simulationListeners.onGameWon.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onGameWon() {
        if (difficultyGateway.getDifficulty() == Difficulty.Hell) {
            addAmount(1);
        }
    }

    @Override
    public String getSinceVersion() {
        return "1.5.9";
    }

    @Override
    public String getTitle() {
        return "No pain, no gain";
    }

    @Override
    public String getDescription() {
        return "Win a game on Hell difficulty!";
    }

}
