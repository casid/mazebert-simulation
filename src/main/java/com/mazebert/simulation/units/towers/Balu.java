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

public strictfp class Balu extends Tower {

    public Balu() {
        setBaseCooldown(4.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.7f);
        setDamageSpread(0.05f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new BaluCuddle());
        addAbility(new BaluSplash());
    }

    @Override
    public Changelog getChangelog() {
        return new Changelog(
                new ChangelogEntry(Sim.v10, false, 2013)
        );
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.005f;
    }

    @Override
    public String getName() {
        return "Balu the Bear";
    }

    @Override
    public String getDescription() {
        return "The fluffiest, cuddliest bear in the world.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 64;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }

    @Override
    public String getModelId() {
        return "balu_the_bear";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 14;
    }

    @Override
    public void populateCustomTowerBonus(CustomTowerBonus bonus) {
        bonus.title = "Cuddled:";
        bonus.value = getAbility(BaluCuddle.class).getCuddleAmount() + "x";
    }
}
