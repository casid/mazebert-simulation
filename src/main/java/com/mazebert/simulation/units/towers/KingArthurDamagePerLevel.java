package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.DamageWithLevelBonusAbility;

public strictfp class KingArthurDamagePerLevel extends DamageWithLevelBonusAbility {
    public static final float DAMAGE_LEVEL_BONUS = 0.01f;

    public KingArthurDamagePerLevel() {
        super(0.0f, DAMAGE_LEVEL_BONUS);
    }

    @Override
    public boolean isVisibleToUser() {
        return false;
    }
}
