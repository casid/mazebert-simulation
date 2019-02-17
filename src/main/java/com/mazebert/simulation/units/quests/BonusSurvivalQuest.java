package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnBonusRoundSurvivedListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class BonusSurvivalQuest extends Quest implements OnBonusRoundSurvivedListener {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private int initialAmount = -1;

    public BonusSurvivalQuest() {
        super(QuestReward.Big, 800);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        simulationListeners.onBonusRoundSurvived.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        simulationListeners.onBonusRoundSurvived.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onBonusRoundSurvived(int seconds) {
        if (initialAmount == -1) {
            initialAmount = getCurrentAmount();
        }

        int delta = seconds - (getCurrentAmount() - initialAmount);
        if (delta > 0) {
            addAmount(delta);
        }
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Survivor";
    }

    @Override
    public String getDescription() {
        return "Survive " + getRequiredAmount() + " seconds in the bonus round.";
    }
}
