package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class Gargoyle extends Tower {

    public Gargoyle() {
        setBaseCooldown(5.0f);
        setBaseRange(2.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.9f);
        setDamageSpread(0.8f);
        setGender(Gender.Male);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/stone-hit.mp3"));
        addAbility(new InstantDamageAbility());
        addAbility(new GargoyleKnockback());
    }

    @Override
    public String getName() {
        return "Gargoyle";
    }

    @Override
    public String getDescription() {
        return "Begone, heathen!";
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
        return "1.9";
    }

    @Override
    public String getModelId() {
        return "gargoyle";
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.0f;
    }

    @Override
    public String getAuthor() {
        return "jhoijhoi & shimakura";
    }
}
