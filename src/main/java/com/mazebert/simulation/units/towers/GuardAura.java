package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.items.GuardLanceAbility;

public strictfp class GuardAura extends AuraAbility<Tower, Tower> {

    private final float baseDamageBonus;

    public GuardAura(float range) {
        this(range, Sim.context().version >= Sim.vDoLEnd ? 6 : 4);
    }

    public GuardAura(float range, float baseDamageBonus) {
        super(CardCategory.Tower, Tower.class, Sim.context().version >= Sim.v19 ? 0 : range);
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
            adjustGuardBonus(+1);
        }
    }

    @Override
    protected void onAuraLeft(Tower unit) {
        if (unit != getUnit()) {
            adjustGuardBonus(-1);
        }
    }

    protected void adjustGuardBonus(float sign) {
        getUnit().addAddedAbsoluteBaseDamage(sign * baseDamageBonus);
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
