package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.AttackType;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;

public strictfp class TrainingDummy extends Tower {

    public TrainingDummy() {
        setBaseCooldown(1.0f);
        setBaseRange(1.0f);
        setAttackType(AttackType.All);
        setStrength(0.0f);
        setDamageSpread(0.0f);
        setGender(Gender.Unknown);
        setElement(Element.Light);

        addAbility(new TrainingDummySpawn());
    }

    @Override
    public String getName() {
        return "Training Dummy";
    }

    @Override
    public String getDescription() {
        return "A magical training\n(does not attack)";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Uncommon;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public String getSinceVersion() {
        return "2.0";
    }

    @Override
    public String getModelId() {
        return "adventurer"; // TODO!!
    }

    @Override
    protected float getGoldCostFactor() {
        return 0.08f; // TODO!!
    }

    @Override
    public String getAuthor() {
        return "sweisdapro";
    }
}
