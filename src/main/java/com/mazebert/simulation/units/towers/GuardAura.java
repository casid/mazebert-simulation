package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;

public strictfp class GuardAura extends AuraAbility<Tower, Tower> {

    private static final float baseDamageBonus = 2;

    public GuardAura(float range) {
        super(CardCategory.Tower, Tower.class, range);
    }

    @Override
    protected void onAuraEntered(Tower unit) {
        if (unit != getUnit()) {
            getUnit().addAddedAbsoluteBaseDamage(baseDamageBonus);
        }
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        if (unit != getUnit()) {
            getUnit().addAddedAbsoluteBaseDamage(-baseDamageBonus);
        }
    }

    @Override
    protected boolean isQualifiedForAura(Tower unit) {
        return unit instanceof Guard || unit instanceof Templar;
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
