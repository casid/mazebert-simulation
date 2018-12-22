package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.ExperienceWithLevelBonusAbility;

public strictfp class NorlsFuryAmuletAbility extends ExperienceWithLevelBonusAbility {
    public NorlsFuryAmuletAbility() {
        super(0.05f, 0.004f);
    }

    @Override
    public String getTitle() {
        return "Return to me, safe and sound.";
    }
}
