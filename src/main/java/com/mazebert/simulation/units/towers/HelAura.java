package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class HelAura extends AuraAbility<Tower, Creep> {

    private int helheimPopulation;

    public HelAura() {
        super(CardCategory.Tower, Creep.class);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
    }

    @Override
    protected void dispose(Tower unit) {
        super.dispose(unit);
    }

    @Override
    protected void onAuraEntered(Creep creep) {
        HelAuraEffect effect = creep.addAbilityStack(getUnit(), HelAuraEffect.class);
        if (effect != null) {
            effect.applyEffect(helheimPopulation / HelAuraEffect.CREEPS_PER_ARMOR);
        }
    }

    @Override
    protected void onAuraLeft(Creep creep) {
        creep.removeAbilityStack(getUnit(), HelAuraEffect.class);
        creep.onTargetReached.dispatch(creep);
    }

    public void increaseHelheimPopulation() {
        ++helheimPopulation;
    }

    public int getHelheimPopulation() {
        return helheimPopulation;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Helheim";
    }

    @Override
    public String getDescription() {
        return "Icy winds of Helheim slow creeps in range by " + format.percent(1.0f - HelAuraEffect.SPEED_MODIFIER) + "% and reduce creep armor. Creeps that escape Helheim count as leaked.";
    }

    @Override
    public String getLevelBonus() {
        return "-1 armor per " + HelAuraEffect.CREEPS_PER_ARMOR + " creeps that did not escape Helheim";
    }

    @Override
    public String getIconFile() {
        return "0080_freeze_enemy_512";
    }
}
