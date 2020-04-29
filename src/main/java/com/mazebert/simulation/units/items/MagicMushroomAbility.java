package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.SpeedWithLevelBonusAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class MagicMushroomAbility extends SpeedWithLevelBonusAbility {
    public static final float damageReduction = 0.2f;

    public MagicMushroomAbility() {
        super(0.2f, 0.01f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        if (Sim.context().version < Sim.v17) {
            unit.addAddedRelativeBaseDamage(-damageReduction);
        }
    }

    @Override
    protected void dispose(Tower unit) {
        if (Sim.context().version < Sim.v17) {
            unit.addAddedRelativeBaseDamage(+damageReduction);
        }
        super.dispose(unit);
    }

    @Override
    public String getTitle() {
        return "A Nice Trip";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(-damageReduction) + " damage.\n" + super.getLevelBonus();
    }
}
