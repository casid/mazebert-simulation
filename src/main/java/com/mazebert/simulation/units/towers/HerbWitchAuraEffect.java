package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttributeWithLevelBonusEffect;

public strictfp class HerbWitchAuraEffect extends AttributeWithLevelBonusEffect {

    public HerbWitchAuraEffect(Tower origin) {
        super(origin, 0.1f, 0.005f);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addAttackSpeed(amount);
    }
}
