package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Dandelion extends Tower {
    public Dandelion() {
        setBaseCooldown(2.5f);
        setBaseRange(2.0f);
        setAttackType(AttackType.All);
        setStrength(0.75f);
        setDamageSpread(0.21f);
        setGender(Gender.Unknown);
        setElement(Element.Nature);

        addAbility(new AttackAbility());
        addAbility(new DandelionSplashAbility());
        addAbility(new ProjectileDamageAbility(118));
    }

    @Override
    public String getName() {
        return "Dandelion";
    }

    @Override
    public String getDescription() {
        return "The devastating blast of this dandelion can hurt many creeps at once.";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.1f;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "0.1";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return -6;
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public String getModelId() {
        return "dandelion";
    }
}
