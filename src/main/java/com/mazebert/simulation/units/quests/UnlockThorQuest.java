package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class UnlockThorQuest extends UnlockGodQuest {

    @Override
    protected TowerType getGod() {
        return TowerType.Thor;
    }
}
