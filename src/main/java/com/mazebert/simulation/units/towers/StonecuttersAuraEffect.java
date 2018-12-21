package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.StackableByOriginAbility;

public strictfp class StonecuttersAuraEffect extends StackableByOriginAbility<Tower> {
    public static final float DAMAGE_BONUS = 0.03f;

    private float damageBonusFal = 0.0f;
    private float damageBonusBer = 0.0f;
    private float damageBonusVex = 0.0f;

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
            damageBonusFal = DAMAGE_BONUS * memberLevel;
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
