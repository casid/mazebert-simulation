package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;

public strictfp class Unicorn extends Tower {

    public Unicorn() {
        setBaseCooldown(5.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Female);
        setElement(Element.Light);

        addAbility(new UnicornDeathAbility());
        addAbility(new UnicornExperienceAbility());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.75f;
    }

    @Override
    public String getName() {
        return "Rainbow Unicorn";
    }

    @Override
    public String getDescription() {
        return "The treasure at the end of the rainbow.\n(does not attack)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 50;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "unicorn";
    }
}
