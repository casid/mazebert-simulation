package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.Gender;

public strictfp class Yggdrasil extends Tower {

    public Yggdrasil() {
        setBaseCooldown(5.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Unknown);
        if (version >= Sim.v20) {
            setElement(Element.Unknown);
        } else {
            setElement(Element.Nature);
        }

        addAbility(new YggdrasilBranchLegacyAbility());
        addAbility(new YggdrasilPotionLegacyAbility());
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
        return "The Great Tree of Life.\n(does not attack)";
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
        if (version >= Sim.v20) {
            return "2.1";
        }
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
