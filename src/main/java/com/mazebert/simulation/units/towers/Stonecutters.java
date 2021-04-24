package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.changelog.Changelog;
import com.mazebert.simulation.changelog.ChangelogEntry;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class Stonecutters extends Tower {

    private final StonecuttersAura aura;

    public Stonecutters() {
        setBaseCooldown(8.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Fal);
        setStrength(1.0f);
        setDamageSpread(0.4f);
        setGender(Gender.Unknown);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(aura = new StonecuttersAura());
        addAbility(new StonecuttersBonus());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vRoCEnd, false, 2021, "Reduce damage bonus from 1% to 0.8%"),
                new ChangelogEntry(Sim.v10, false, 2015)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.4f;
    }

    @Override
    public String getName() {
        return "Stonecutter's Temple";
    }

    @Override
    public String getDescription() {
        return "We do! We do!";
    }

    @Override
    public int getItemLevel() {
        return 81;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "1.3";
    }

    @Override
    public String getModelId() {
        return "stonecutters";
    }

    @Override
    public int getImageOffsetOnCardX() {
        return -5;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -5;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Members:";
        bonus.value = "" + getMemberCount();
    }

    public int getMemberCount() {
        return aura.getMemberCount();
    }
}
