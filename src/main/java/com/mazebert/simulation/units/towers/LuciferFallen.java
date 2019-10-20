package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.InstantDamageAbility;

public strictfp class LuciferFallen extends Tower {

    public LuciferFallen() {
        setBaseCooldown(2.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.8f);
        setDamageSpread(0.5f);
        setGender(Gender.Male);
        setElement(Element.Light);

        addAbility(new AttackAbility());
        addAbility(new InstantDamageAbility());
        addAbility(new LuciferFallenLightning());
        addAbility(new LuciferFallenRestInPiece());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0;
    }

    @Override
    public String getName() {
        return "Lucifer";
    }

    @Override
    public String getDescription() {
        return "...the lower you fall.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 90;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "yggdrasil"; // TODO visuals
    }

    @Override
    public boolean isDark() {
        return true;
    }
}
