package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.units.heroes.HeroType;

public strictfp class KillCultistsOfYigQuest extends KillCultistsQuest {
    public KillCultistsOfYigQuest() {
        super(WaveType.CultistOfYig);
    }

    @Override
    protected HeroType getHeroToUnlock() {
        return HeroType.Yig;
    }
}
