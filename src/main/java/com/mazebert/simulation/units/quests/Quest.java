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

    private transient Wizard wizard;

    public Quest(QuestReward reward, int requiredAmount) {
        this.type = null; // TODO
        this.id = 0; // TODO
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

}
