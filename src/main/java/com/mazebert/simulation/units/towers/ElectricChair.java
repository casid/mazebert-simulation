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

public strictfp class ElectricChair extends Tower {

    public ElectricChair() {
        setBaseCooldown(2.5f);
        setBaseRange(1);
        setAttackType(AttackType.Vex);
        setStrength(0.55f);
        setDamageSpread(0.5f);
        setGender(Gender.Unknown);
        setElement(Element.Metropolis);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new ElectricChairLightning());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.vDoLEnd, false, 2020, "+1 chain every 7 instead of 14 levels"),
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    public String getName() {
        return "Electric Chair";
    }

    @Override
    public String getDescription() {
        return "When creeps come into range, the Electric Chair can't hold back its energy.";
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
        return 8;
    }

    @Override
    public String getSinceVersion() {
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "electric_chair";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.25f;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Chains:";
        bonus.value = "+" + getAbility(ElectricChairLightning.class).getMaxChains();
    }
}
