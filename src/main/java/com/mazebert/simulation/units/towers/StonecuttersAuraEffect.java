package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.StackableByOriginAbility;

public strictfp class StonecuttersAuraEffect extends StackableByOriginAbility<Tower> {
    private float damageBonusFal = 0.0f;

    public void update(int memberLevel) {
        getUnit().addDamageAgainstFal(-damageBonusFal);

        if (getUnit().getLevel() >= StonecuttersAura.MEMBER_LEVEL_REQUIREMENT) {
            damageBonusFal = 0.03f * memberLevel;
            getUnit().addDamageAgainstFal(damageBonusFal);
        } else {
            damageBonusFal = 0.0f;
        }
    }
}
