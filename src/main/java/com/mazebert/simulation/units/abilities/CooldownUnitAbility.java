package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.units.CooldownUnit;
import com.mazebert.simulation.units.Unit;

public abstract strictfp class CooldownUnitAbility<U extends Unit & CooldownUnit> extends CooldownAbility<U> {
    @Override
    protected float getCooldown() {
        return getUnit().getCooldown();
    }
}
