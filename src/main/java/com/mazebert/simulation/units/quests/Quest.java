package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.hash.Hash;
import com.mazebert.simulation.hash.Hashable;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.wizards.Wizard;

public abstract strictfp class Quest extends Ability<Wizard> implements Hashable {
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

    @Override
    public void hash(Hash hash) {
        hash.add(id);
        hash.add(reward);
        hash.add(requiredAmount);
        hash.add(currentAmount);
    }

    public abstract String getSinceVersion();

    public int getCurrentAmount() {
        return currentAmount;
    }

    public int getRequiredAmount() {
        return requiredAmount;
    }

    protected void addAmount(int amount) {
        currentAmount += amount;
        if (isComplete()) {
            currentAmount = requiredAmount;
            getUnit().onQuestCompleted.dispatch(getUnit(), this);

            getUnit().removeAbility(this);
        }
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

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
    }
}
