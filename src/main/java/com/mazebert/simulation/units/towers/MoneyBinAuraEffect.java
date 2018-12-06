package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttributeWithLevelBonusEffect;

public strictfp class MoneyBinAuraEffect extends AttributeWithLevelBonusEffect {

    public MoneyBinAuraEffect(Tower origin) {
        super(origin, 0.6f, 0.01f);
    }

    @Override
    protected void addToAttribute(float amount) {
        getUnit().addGoldModifer(amount);
    }
}
