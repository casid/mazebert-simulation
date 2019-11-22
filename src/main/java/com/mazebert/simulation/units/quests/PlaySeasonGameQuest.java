package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnGameStartedListener;
import com.mazebert.simulation.units.wizards.Wizard;

public abstract strictfp class PlaySeasonGameQuest extends Quest implements OnGameStartedListener {
    public PlaySeasonGameQuest() {
        super(QuestReward.Small, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        Sim.context().simulationListeners.onGameStarted.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        Sim.context().simulationListeners.onGameStarted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onGameStarted() {
        addAmount(1);
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
