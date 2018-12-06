package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttributeWithLevelBonusEffect;

public strictfp class PubAuraEffect extends AttributeWithLevelBonusEffect {

    public PubAuraEffect(Tower origin) {
        super(origin, 0.1f, 0.005f);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addAddedRelativeBaseDamage(amount);
    }
}
