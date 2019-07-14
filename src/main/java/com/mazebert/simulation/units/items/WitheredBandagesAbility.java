package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.SpeedWithLevelBonusAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class WitheredBandagesAbility extends SpeedWithLevelBonusAbility {
    private static final float luckBonus = 0.1f;

    public WitheredBandagesAbility() {
        super(0.1f, 0.03f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.addLuck(luckBonus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addLuck(-luckBonus);
        super.dispose(unit);
    }

    @Override
    public String getTitle() {
        return "Curse of the Pharao";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(luckBonus) + " luck\n" +
                format.percentWithSignAndUnit(bonus) + " attack speed <c=#fff8c6>(" + format.percent(bonusPerLevel) + "%/level)</c>";
    }

    @Override
    public String getLevelBonus() {
        return null;
    }
}
