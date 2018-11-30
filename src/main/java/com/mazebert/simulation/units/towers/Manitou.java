package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;

public strictfp class Manitou extends Tower {

    public Manitou() {
        setBaseCooldown(5.0f);
        setBaseRange(3.0f);
        setAttackType(AttackType.Vex);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Male);
        setElement(Element.Nature);

        addAbility(new ManitouAura());
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.6f;
    }

    @Override
    public String getName() {
        return "Manitou";
    }

    @Override
    public String getDescription() {
        return "The Great Spirit of the West.";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 60;
    }

    @Override
    public String getSinceVersion() {
        return "0.3";
    }

    @Override
    public String getModelId() {
        return "manitou";
    }

    @Override
    public String getAuthor() {
        return "Ulrich Herbricht";
    }

    @Override
    public int getImageOffsetOnCardY() {
        return 8;
    }
}
