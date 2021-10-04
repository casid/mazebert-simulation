package com.mazebert.simulation.units.items.prophecies;

import com.mazebert.simulation.units.quests.QuestType;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class UnlockHelProphecy extends UnlockGodProphecy {

    @Override
    protected TowerType getGod() {
        return TowerType.Hel;
    }

    @Override
    protected QuestType getQuest() {
        return QuestType.UnlockHelQuest;
    }
}
