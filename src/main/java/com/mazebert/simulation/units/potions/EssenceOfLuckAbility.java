package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentLuckWithLevelBonusAbility;

public strictfp class EssenceOfLuckAbility extends PermanentLuckWithLevelBonusAbility {
    public EssenceOfLuckAbility() {
        super(0.33f, 0.003f);
    }

    @Override
    public String getTitle() {
        return "Serendipity";
    }

    @Override
    public String getDescription() {
        return "+ " + format.percent(bonus) + "% luck";
    }
}
