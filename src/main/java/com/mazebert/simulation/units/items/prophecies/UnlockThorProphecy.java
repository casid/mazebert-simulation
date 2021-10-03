package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class UnlockThorProphecy extends UnlockGodProphecy {

    @Override
    protected TowerType getGod() {
        return TowerType.Thor;
    }

    @Override
    protected QuestType getQuest() {
        return QuestType.UnlockThorQuest;
    }
}
