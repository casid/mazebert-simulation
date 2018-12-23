package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.FalDamageWithLevelBonusAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class WitheredToadstoolAbility extends FalDamageWithLevelBonusAbility {
    private static final float goldMalus = -0.3f;

    public WitheredToadstoolAbility() {
        super(0.35f, 0.012f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addGoldModifer(goldMalus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addGoldModifer(-goldMalus);
        super.dispose(unit);
    }

    @Override
    public String getTitle() {
        return "Rotten Touch";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(goldMalus) + " " + getCurrency().pluralLowercase + " bounty\n+ " +
                format.percentWithSignAndUnit(bonus) + " damage vs Fal <c=#fff8c6>(" + format.percent(bonusPerLevel) + "%/level)</c>";
    }

    @Override
    public String getLevelBonus() {
        return null;
    }
}
