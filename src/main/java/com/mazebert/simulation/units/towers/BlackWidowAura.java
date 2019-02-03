package com.mazebert.simulation.units.towers;

import com.mazebert.java8.Supplier;
import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class BlackWidowAura extends AuraAbility<Tower, Creep> implements Supplier<BlackWidowAuraEffect> {
    public BlackWidowAura() {
        super(CardCategory.Tower, Creep.class, 3);
    }

    @Override
    protected void onAuraEntered(Creep unit) {
        unit.addAbilityStack(getUnit(), BlackWidowAuraEffect.class, this);
    }

    @Override
    protected void onAuraLeft(Creep unit) {
        unit.removeAbilityStack(getUnit(), BlackWidowAuraEffect.class);
    }

    @Override
    public BlackWidowAuraEffect get() {
        return new BlackWidowAuraEffect(1.2f + getUnit().getLevel() * 0.005f, 1.5f + getUnit().getLevel() * 0.01f);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Last will";
    }

    @Override
    public String getDescription() {
        return "Creeps in 3 range are seduced. Their item chance is increased by 20% and they grant 50% more " + getCurrency().pluralLowercase + ".";
    }

    @Override
    public String getIconFile() {
        return "0032_ring_512";
    }

    @Override
    public String getLevelBonus() {
        return "+ 0.5% item chance per level\n+ 1% " + getCurrency().pluralLowercase + " per level";
    }
}
