package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;

public strictfp class Yggdrasil extends Tower {

    public Yggdrasil() {
        setBaseCooldown(5.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Unknown);
        setElement(Element.Nature);

        addAbility(new YggdrasilBranchAbility());
        addAbility(new YggdrasilPotionAbility());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.24f;
    }

    @Override
    public String getName() {
        return "Yggdrasil";
    }

    @Override
    public String getDescription() {
        return "The Great Tree of Life.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 77;
    }

    @Override
    public String getSinceVersion() {
        return "1.5";
    }

    @Override
    public String getModelId() {
        return "yggdrasil";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }
}
