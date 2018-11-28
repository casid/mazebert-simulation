package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentDropsWithLevelBonusAbility;

public strictfp class UncommonDropsAbility extends PermanentDropsWithLevelBonusAbility {
    public UncommonDropsAbility() {
        super(0.2f, 0.0008f, 0.2f, 0.0008f);
    }
}
