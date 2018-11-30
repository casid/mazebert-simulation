package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttributeWithLevelBonusEffect;

public strictfp class GaneshaExperienceAuraEffect extends AttributeWithLevelBonusEffect {

    public GaneshaExperienceAuraEffect(Tower origin) {
        super(origin, 0.2f, 0.005f);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addExperienceModifier(amount);
    }
}
