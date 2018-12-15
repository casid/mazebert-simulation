package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class DarkForge extends Tower {

    public DarkForge() {
        setBaseCooldown(5.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Fal);
        setStrength(1.5f);
        setDamageSpread(0.1f);
        setGender(Gender.Male);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new DarkForgeCraft());
        addAbility(new DarkForgeCurse());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.77f;
    }

    @Override
    public String getName() {
        return "The Dark Forge";
    }

    @Override
    public String getDescription() {
        return "This forge crafts powerful items, but enchanted with a vicious curse.";
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public String getSinceVersion() {
        return "0.7";
    }

    @Override
    public String getModelId() {
        return "the_forge";
    }

    @Override
    public String getAuthor() {
        return "florieger";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 4;
    }
}
