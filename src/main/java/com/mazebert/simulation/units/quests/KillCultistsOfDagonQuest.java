package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.units.heroes.HeroType;

public strictfp class KillCultistsOfDagonQuest extends KillCultistsQuest {
    public KillCultistsOfDagonQuest() {
        super(WaveType.CultistOfDagon);
    }

    @Override
    protected HeroType getHeroToUnlock() {
        return HeroType.Dagon;
    }
}
