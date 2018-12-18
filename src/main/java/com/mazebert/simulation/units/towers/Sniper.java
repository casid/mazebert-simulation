package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;

public strictfp class Sniper extends Tower {

    public Sniper() {
        setBaseCooldown(3.0f);
        setBaseRange(4.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.5f);
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Metropolis);

        addAbility(new SniperBurst());
        addAbility(new SniperAttack());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.05f;
    }

    @Override
    public String getName() {
        return "Scarface";
    }

    @Override
    public String getDescription() {
        return "Say hello to my little friend!";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getModelId() {
        return "sniper";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 16;
    }

    @Override
    public String getAuthor() {
        return "Nomad";
    }
}