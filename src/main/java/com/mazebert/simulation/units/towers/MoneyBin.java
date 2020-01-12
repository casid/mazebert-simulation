package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Context;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class MoneyBin extends Tower {

    public MoneyBin() {
        setBaseCooldown(3.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.4f);
        setDamageSpread(0.4f);
        setGender(Gender.Unknown);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new MoneyBinInterest());
        addAbility(new MoneyBinAura());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.01f;
    }

    @Override
    public String getName() {
        return "Money Bin";
    }

    @Override
    public String getDescription() {
        return "You will swim in " + Context.currency.pluralLowercase + ".";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 40;
    }

    @Override
    public String getSinceVersion() {
        return "0.4";
    }

    @Override
    public String getModelId() {
        return "moneybin";
    }

}
