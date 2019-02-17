package com.mazebert.simulation.units.quests;

public strictfp class BuyBlackMarketOfferQuest extends Quest {

    public BuyBlackMarketOfferQuest() {
        super(QuestReward.Huge, 1);
    }

    @Override
    public boolean isSimulated() {
        return false;
    }

    @Override
    public boolean isHidden() {
        return true;
    }

    @Override
    public String getSinceVersion() {
        return "1.1.0";
    }

    @Override
    public String getTitle() {
        return "Black Market Customer";
    }

    @Override
    public String getDescription() {
        return "A pleasure, friend. Come again!";
    }
}
