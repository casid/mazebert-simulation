package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;

public strictfp class LuciferFallen extends Tower {

    public LuciferFallen() {
        setBaseCooldown(0.5f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Fal);
        setStrength(1.4f);
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Light);

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
        return "todo";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Legendary;
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
