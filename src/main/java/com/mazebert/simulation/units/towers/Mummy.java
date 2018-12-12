package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.projectiles.ProjectileViewType;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.ProjectileDamageAbility;

public strictfp class Mummy extends Tower {

    public Mummy() {
        setBaseCooldown(1.2f);
        setBaseRange(4.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.95f);
        setDamageSpread(0.1f);
        setGender(Gender.Female);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new MummyStumble());
        addAbility(new ProjectileDamageAbility(ProjectileViewType.Bandage, 11.8f));
    }

    @Override
    public String getName() {
        return "Miss Jilly";
    }

    @Override
    public String getDescription() {
        return "Interesting fact: Likes to wrap herself and her victims in toilet paper.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "0.9";
    }

    @Override
    public String getModelId() {
        return "mummy";
    }

    @Override
    public String getAuthor() {
        return "Jil Dorn";
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.98f;
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 22;
    }
}
