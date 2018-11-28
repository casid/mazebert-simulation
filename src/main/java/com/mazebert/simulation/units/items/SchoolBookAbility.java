package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.ExperienceWithLevelBonusAbility;

public strictfp class SchoolBookAbility extends ExperienceWithLevelBonusAbility {
    public SchoolBookAbility() {
        super(0.1f, 0.0f);
    }

    @Override
    public String getTitle() {
        return "Much to learn you have";
    }
}
