package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class UnlockLokiProphecy extends UnlockGodProphecy {

    @Override
    protected TowerType getGod() {
        return TowerType.Loki;
    }

    @Override
    protected QuestType getQuest() {
        return QuestType.UnlockLokiQuest;
    }
}
