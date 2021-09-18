package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.AuraAbility;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.creeps.effects.ReviveEffect;

public strictfp class IdunReviveAura extends AuraAbility<Tower, Creep> {

    public IdunReviveAura() {
        super(CardCategory.Tower, Creep.class);
    }

    @Override
    protected void onAuraEntered(Creep creep) {
        if (!creep.hasAbility(ReviveEffect.class)) {
            creep.addAbility(new IdunReviveAuraEffect());
        }
    }

    @Override
    protected void onAuraLeft(Creep creep) {
        creep.removeAbility(IdunReviveAuraEffect.class);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Fertility";
    }

    @Override
    public String getDescription() {
        return "Creeps that die in range have a chance to be revived.";
    }

    @Override
    public String getIconFile() {
        return "0013_flowers_512";
    }
}
