package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.items.GuardLanceAbility;

public strictfp class GuardAura extends AuraAbility<Tower, Tower> {

    private final float baseDamageBonus;

    public GuardAura(float range) {
        this(range, 2);
    }

    public GuardAura(float range, float baseDamageBonus) {
        super(CardCategory.Tower, Tower.class, range);
        this.baseDamageBonus = baseDamageBonus;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        setRange(unit.getRange());
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
        return unit.hasAbility(GuardAura.class) || unit.hasAbility(GuardLanceAbility.class);
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
