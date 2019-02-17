package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class KnusperHexeQuest extends Quest {
    public KnusperHexeQuest() {
        super(QuestReward.Medium, 700);
    }

    @Override
    public String getSinceVersion() {
        return "1.3.0";
    }

    @Override
    public String getTitle() {
        return "Nibble, nibble, gnaw";
    }

    @Override
    public String getDescription() {
        return "Let " + format.card(TowerType.KnusperHexe) + " eat " + getRequiredAmount() + " creeps.";
    }
}
