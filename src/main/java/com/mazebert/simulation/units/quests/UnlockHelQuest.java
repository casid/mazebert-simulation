package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.units.towers.TowerType;

public strictfp class UnlockHelQuest extends UnlockGodQuest {

    @Override
    protected TowerType getGod() {
        return TowerType.Hel;
    }
}
