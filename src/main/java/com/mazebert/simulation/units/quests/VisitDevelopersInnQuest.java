package com.mazebert.simulation.units.quests;

public strictfp class VisitDevelopersInnQuest extends Quest {

    public VisitDevelopersInnQuest() {
        super(QuestReward.Small, 1);
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
        return "Innkeeper's regular";
    }

    @Override
    public String getDescription() {
        return "Thank you, come again! :-)";
    }
}
