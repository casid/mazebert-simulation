package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class KillChallengesQuest extends Quest {

    public KillChallengesQuest() {
        super(QuestReward.Medium, 7);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
    }

    @Override
    protected void dispose(Wizard unit) {
        super.dispose(unit);
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "Headhunter";
    }

    @Override
    public String getDescription() {
        return "Kill " + requiredAmount + format.colored(" Challenges", 0xffff00) + "!";
    }
}
