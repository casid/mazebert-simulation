package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.listeners.OnAllPotionsConsumedListener;
import com.mazebert.simulation.units.potions.PotionType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class DrinkAllPotionsQuest extends Quest implements OnAllPotionsConsumedListener {
    public DrinkAllPotionsQuest() {
        super(QuestReward.Small, 1);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        for (int i = 0; i < 50; ++i) {
            unit.potionStash.add(PotionType.DrinkAll);
        }
        unit.potionStash.onAllPotionsConsumed.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        unit.potionStash.onAllPotionsConsumed.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onAllPotionsConsumed() {
        addAmount(1);
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public boolean isAllowedInTutorial() {
        return false;
    }

    @Override
    public String getSinceVersion() {
        return "1.3.0";
    }

    @Override
    public String getTitle() {
        return "Cheers!";
    }

    @Override
    public String getDescription() {
        return "From now on you win every drinking contest.";
    }
}
