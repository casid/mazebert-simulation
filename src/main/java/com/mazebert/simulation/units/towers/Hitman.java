package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.DelayedDamageAbility;

public strictfp class Hitman extends Tower {
    public Hitman() {
        setBaseCooldown(5.0f);
        setBaseRange(6.0f);
        setAttackType(AttackType.Fal);
        setStrength(1.5f);
        setDamageSpread(1.0f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new DelayedDamageAbility(1.0f));
        addAbility(new HitmanHeadshotAbility());
    }

    @Override
    public String getName() {
        return "Hitman";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.2";
    }

    @Override
    public String getModelId() {
        return "hitman";
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getDescription() {
        return "This professional killer prefers to attack from the distance, hidden in shadows.";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.3f;
    }
}
