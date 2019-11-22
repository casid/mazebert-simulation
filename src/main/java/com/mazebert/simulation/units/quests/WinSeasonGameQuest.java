package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnGameWonListener;
import com.mazebert.simulation.units.wizards.Wizard;

public abstract strictfp class WinSeasonGameQuest extends Quest implements OnGameWonListener {
    public WinSeasonGameQuest() {
        super(QuestReward.Big, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        Sim.context().simulationListeners.onGameWon.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        Sim.context().simulationListeners.onGameWon.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onGameWon() {
        addAmount(1);
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
