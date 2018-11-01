package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;
import com.mazebert.simulation.units.abilities.DamageAbility;
import com.mazebert.simulation.units.abilities.DelayedDamageAbility;

public class Hitman extends Tower {
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
}
