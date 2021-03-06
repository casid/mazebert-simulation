package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnGameWonListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class WinAprilFoolsGameQuest extends Quest implements OnGameWonListener {
    public WinAprilFoolsGameQuest() {
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
        if (Sim.context().gameGateway.getGame().isAprilFoolsGame()) {
            addAmount(1);
        }
    }

    @Override
    public String getSinceVersion() {
        return "2.2.2";
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getTitle() {
        return "April Fools";
    }

    @Override
    public String getDescription() {
        return "Win an april fools game!";
    }
}
