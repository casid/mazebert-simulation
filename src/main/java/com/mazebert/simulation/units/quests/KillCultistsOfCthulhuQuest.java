package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.WaveType;
import com.mazebert.simulation.units.heroes.HeroType;

public strictfp class KillCultistsOfCthulhuQuest extends KillCultistsQuest {
    public KillCultistsOfCthulhuQuest() {
        super(WaveType.CultistOfCthulhu);
    }

    @Override
    protected HeroType getHeroToUnlock() {
        return HeroType.Cthulhu;
    }
}
