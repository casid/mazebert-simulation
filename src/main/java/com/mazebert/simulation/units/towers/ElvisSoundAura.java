package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class ElvisSoundAura extends AuraAbility<Tower, Creep> {
    public ElvisSoundAura() {
        super(Creep.class, 1);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        ElvisAuraEffect effect = unit.addAbilityStack(getUnit(), ElvisAuraEffect.class);
        effect.setDuration(2.0f + getUnit().getLevel() * 0.01f);
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        unit.removeAbilityStack(getUnit(), ElvisAuraEffect.class);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Worst - Show - Ever";
    }

    @Override
    public String getDescription() {
        return "Creeps entering his range are shocked and slowed by 50% for 2 seconds. After that, their speed is increased by 25% until they escaped the horrible show.";
    }

    @Override
    public String getIconFile() {
        return "fabric_roll_512";
    }

    @Override
    public String getLevelBonus() {
        return "+0.1 seconds per level";
    }
}
