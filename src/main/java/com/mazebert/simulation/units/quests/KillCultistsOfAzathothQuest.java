package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.units.heroes.HeroType;

public strictfp class KillCultistsOfAzathothQuest extends KillCultistsQuest {
    public KillCultistsOfAzathothQuest() {
        super(WaveType.CultistOfAzathoth);
    }

    @Override
    protected HeroType getHeroToUnlock() {
        return HeroType.Azathoth;
    }
}
