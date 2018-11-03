package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.units.Gender;
import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.DelayedDamageAbility;

public strictfp class Hitman extends Tower {
    public Hitman() {
        setBaseCooldown(5.0f);
        setBaseRange(6.0f);
        setDamageSpread(1.0f);
        setGender(Gender.Male);

        addAbility(new AttackAbility());
        addAbility(new DelayedDamageAbility(1.0f));
    }

    @Override
    public String getName() {
        return "Hitman";
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Common;
    }

    @Override
    public String getModelId() {
        return "hitman";
    }
}
