package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class UnlockLokiQuest extends UnlockGodQuest {

    @Override
    protected TowerType getGod() {
        return TowerType.Loki;
    }
}
