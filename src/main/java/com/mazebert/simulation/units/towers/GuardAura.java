package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class GuardAura extends AuraAbility<Guard, Guard> {

    private static final float baseDamageBonus = 2;

    public GuardAura() {
        super(CardCategory.Tower, Guard.class, Guard.RANGE);
    }

    @Override
    protected void onAuraEntered(Guard unit) {
        if (unit != getUnit()) {
            getUnit().addAddedAbsoluteBaseDamage(baseDamageBonus);
        }
    }

    @Override
    protected void onAuraLeft(Guard unit) {
        if (unit != getUnit()) {
            getUnit().addAddedAbsoluteBaseDamage(-baseDamageBonus);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Reinforcement";
    }

    @Override
    public String getDescription() {
        return "+" + format.damage(baseDamageBonus) + " base damage for every other guard in range.";
    }

    @Override
    public String getIconFile() {
        return "0006_handarmor_512";
    }
}
