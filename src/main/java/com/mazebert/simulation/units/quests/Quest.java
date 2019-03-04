package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.wizards.Wizard;

public abstract strictfp class Quest extends Ability<Wizard> {
    public final int id;
    public final QuestType type;
    public final int reward;
    public final int requiredAmount;

    private int currentAmount;

    @SuppressWarnings("ConstantConditions")
    public Quest(QuestReward reward, int requiredAmount) {
        this.type = QuestType.forClass(getClass());
        this.id = type.id;
        this.reward = reward.relics;
        this.requiredAmount = requiredAmount;
    }

    public abstract String getSinceVersion();

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    public void addAmount(int amount) {
        if (amount <= 0) {
            return;
        }

        if (isDisposed()) {
            return;
        }

        currentAmount += amount;
        if (isComplete()) {
            currentAmount = requiredAmount;
            getUnit().onQuestCompleted.dispatch(getUnit(), this);

            getUnit().removeAbility(this);
        }
    }

    protected void questFailed() {
        if (!isHidden()) {
            Sim.context().simulationListeners.showNotification(getUnit(), "Quest failed: " + getTitle());
        }
        getUnit().removeAbility(this);
    }

    public boolean isComplete() {
        return currentAmount >= requiredAmount;
    }

    public boolean isHidden() {
        return false;
    }

    public boolean isSimulated() {
        return true;
    }

    public boolean isAllowedInTutorial() {
        return true;
    }
}
