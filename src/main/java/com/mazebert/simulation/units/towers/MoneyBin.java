package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.*;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class MoneyBin extends Tower {

    public MoneyBin() {
        setBaseCooldown(3.5f);
        if (Sim.context().version >= Sim.vDoLEnd) {
            setBaseRange(3.0f);
        } else {
            setBaseRange(2.0f);
        }
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
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "Aura range is always the same as Money Bin's range.", "Increased range from 2 to 3."),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
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
