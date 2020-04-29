package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.SplashAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class MesserschmidtsReaverAbility extends SplashAbility {

    private static final float attackSpeedMalus = 0.4f;
    private static final float rangeMalus = 0.4f;

    private float currentRangeMalus;

    public MesserschmidtsReaverAbility() {
        super(3, 0.25f);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        currentRangeMalus = rangeMalus * unit.getBaseRange();
        unit.addRange(-currentRangeMalus);
        unit.addAttackSpeed(-attackSpeedMalus);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addRange(currentRangeMalus);
        unit.addAttackSpeed(attackSpeedMalus);
        super.dispose(unit);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "No, Seriously. It's Really Heavy.";
    }

    @Override
    public String getDescription() {
        return "";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(-rangeMalus) + " range.\n" + format.percentWithSignAndUnit(-attackSpeedMalus) + " attack speed\n" + format.percent(getDamageFactor()) + "% splash damage within " + getRange() + " range of the carrier's target.";
    }

    @Override
    public String getIconFile() {
        return null; // should not be displayed when equipped
    }
}
