package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.listeners.OnGoldChangedListener;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class CollectGoldQuest extends Quest implements OnGoldChangedListener {

    public CollectGoldQuest() {
        super(QuestReward.Big, 1000000);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        unit.onGoldChanged.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        super.dispose(unit);
    }

    @Override
    public void onGoldChanged(Wizard wizard, long oldGold, long newGold) {
        addAmount((int)(newGold - oldGold));
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "American Dream";
    }

    @Override
    public String getDescription() {
        return "Collect  " + format.gold(requiredAmount, getCurrency()) + "!";
    }
}
