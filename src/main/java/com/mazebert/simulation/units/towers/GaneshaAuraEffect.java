package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttributeWithLevelBonusEffect;

public strictfp class GaneshaAuraEffect extends AttributeWithLevelBonusEffect {

    public GaneshaAuraEffect(Tower origin) {
        super(origin, 0.2f, 0.005f);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addExperienceModifier(amount);
    }
}
