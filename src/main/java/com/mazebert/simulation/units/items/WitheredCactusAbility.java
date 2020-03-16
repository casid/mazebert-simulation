package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.ArmorPenetrationWithLevelBonusAbility;

public strictfp class WitheredCactusAbility extends ArmorPenetrationWithLevelBonusAbility {
    public static float getBonus() {
        if (Sim.context().version >= Sim.vDoLEnd) {
            return 0.05f;
        }
        if (Sim.context().version >= Sim.vDoL) {
            return 0.03f;
        }
        return 0.15f;
    }

    public static float getBonusPerLevel() {
        if (Sim.context().version >= Sim.vDoLEnd) {
            return 0.0005f;
        }
        if (Sim.context().version >= Sim.vDoL) {
            return 0.0003f;
        }
        return 0.003f;
    }

    public WitheredCactusAbility() {
        super(getBonus(), getBonusPerLevel());
    }

    @Override
    public String getTitle() {
        return "One Thousand Stiches";
    }
}
