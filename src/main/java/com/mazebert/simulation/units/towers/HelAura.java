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
            effect.applyEffect(helheimPopulation / 10);
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
        return "Icy winds of Helheim slow creeps in range by 50% and reduce creep armor. Creeps that escape Helheim count as leaked.";
    }

    @Override
    public String getLevelBonus() {
        return "-1 armor per 10 creeps that did not escape Helheim";
    }

    @Override
    public String getIconFile() {
        return "0080_freeze_enemy_512";
    }
}
