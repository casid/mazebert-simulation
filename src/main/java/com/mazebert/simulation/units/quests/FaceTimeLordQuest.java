package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnTimeLordStartedListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class FaceTimeLordQuest extends Quest implements OnTimeLordStartedListener {
    public FaceTimeLordQuest() {
        super(QuestReward.Fortune, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        Sim.context().simulationListeners.onTimeLordStarted.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        Sim.context().simulationListeners.onTimeLordStarted.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onTimeLordStarted() {
        addAmount(1);
    }

    @Override
    public String getSinceVersion() {
        return "2.0.0";
    }

    @Override
    public String getTitle() {
        return "Time Lord";
    }

    @Override
    public String getDescription() {
        return "Fight Vir, the time itself!";
    }

    @Override
    public boolean isHidden() {
        return true;
    }
}
