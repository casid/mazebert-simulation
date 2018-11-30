package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;

public strictfp class Ganesha extends Tower {

    public Ganesha() {
        setBaseCooldown(1.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Ber);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new GaneshaExperienceAura());
        addAbility(new GaneshaLevelAura());
    }

    @Override
    protected float getGoldCostFactor() {
        return 1.21f;
    }

    @Override
    public String getName() {
        return "Ganesha";
    }

    @Override
    public String getDescription() {
        return "The deity of intellect and wisdom.";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Rare;
    }

    @Override
    public int getItemLevel() {
        return 72;
    }

    @Override
    public String getSinceVersion() {
        return "0.1";
    }

    @Override
    public String getModelId() {
        return "ganesha";
    }
}
