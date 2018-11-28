package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.PermanentCritWithLevelBonusAbility;

public class UncommonCritAbility extends PermanentCritWithLevelBonusAbility {
    public UncommonCritAbility() {
        super(0.2f, 0.002f, 0.016f, 0.0004f);
    }
}
