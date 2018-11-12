package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class Dandelion extends Tower {
    public Dandelion() {
        setBaseCooldown(2.5f);
        setBaseRange(2.0f);
        setStrength(0.75f);
        setDamageSpread(0.21f);
        setGender(Gender.Unknown);

        addAbility(new AttackAbility());
        addAbility(new DandelionSplashAbility());
    }

    @Override
    public String getName() {
        return "Dandelion";
    }

    @Override
    public String getDescription() {
        return "??";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getSinceVersion() {
        return "?";
    }

    @Override
    public String getModelId() {
        return "dandelion";
    }
}
