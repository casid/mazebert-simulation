package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.AttackSoundAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class BloodDemon extends Tower {

    public BloodDemon() {
        setBaseCooldown(0.7f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Darkness);

        addAbility(new AttackAbility());
        addAbility(new AttackSoundAbility("sounds/blood.mp3"));
        addAbility(new InstantDamageAbility());
        addAbility(new BloodDemonSummonBlade());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.6f;
    }

    @Override
    public String getName() {
        return "Blood Demon";
    }

    @Override
    public String getDescription() {
        return "Rhagh-Daaaaarrrhhh.";
    }

    @Override
    public String getAuthor() {
        return "niXful";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
    }

    @Override
    public int getItemLevel() {
        return 80;
    }

    @Override
    public String getSinceVersion() {
        return "1.0";
    }

    @Override
    public String getModelId() {
        return "blood_demon";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }

    @Override
    public boolean isHarmful() {
        return true;
    }
}
