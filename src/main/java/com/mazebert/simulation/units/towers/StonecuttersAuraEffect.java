package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.StackableByOriginAbility;

public strictfp class StonecuttersAuraEffect extends StackableByOriginAbility<Tower> {
    private float damageBonusFal = 0.0f;
    private float damageBonusBer = 0.0f;
    private float damageBonusVex = 0.0f;

    public static float getDamageBonus() {
        int version = Sim.context().version;
        if (version >= Sim.vRoCEnd) {
            return 0.008f;
        }
        if (version >= Sim.v13) {
            return 0.01f;
        }
        return 0.03f;

    }

    @Override
    protected void dispose(Tower unit) {
        removeBonus();
        super.dispose(unit);
    }

    public void update(int memberLevel, boolean bonusForAllDamageTypes) {
        removeBonus();
        addBonus(memberLevel, bonusForAllDamageTypes);
    }

    private void addBonus(int memberLevel, boolean bonusForAllDamageTypes) {
        if (getUnit().getLevel() >= StonecuttersAura.MEMBER_LEVEL_REQUIREMENT) {
            damageBonusFal = getDamageBonus() * memberLevel;
            getUnit().addDamageAgainstFal(damageBonusFal);

            if (bonusForAllDamageTypes) {
                getUnit().addDamageAgainstBer(damageBonusBer = damageBonusFal);
                getUnit().addDamageAgainstVex(damageBonusVex = damageBonusFal);
            }
        }
    }

    private void removeBonus() {
        if (damageBonusFal > 0) {
            getUnit().addDamageAgainstFal(-damageBonusFal);
            getUnit().addDamageAgainstBer(-damageBonusBer);
            getUnit().addDamageAgainstVex(-damageBonusVex);

            damageBonusFal = 0.0f;
            damageBonusBer = 0.0f;
            damageBonusVex = 0.0f;
        }
    }
}
