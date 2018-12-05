package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class Elvis extends Tower {

    public Elvis() {
        setBaseCooldown(2.0f);
        setBaseRange(1);
        setAttackType(AttackType.Vex);
        setStrength(0.9f);
        setDamageSpread(0.33f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new ElvisSoundAura());
    }

    @Override
    public String getName() {
        return "Elvis Imitator";
    }

    @Override
    public String getDescription() {
        return "He is smelly. He can't sing and he can't dance. He disturbs everyone nearby.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 16;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }

    @Override
    public String getModelId() {
        return "elvis";
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.95f;
    }
}
