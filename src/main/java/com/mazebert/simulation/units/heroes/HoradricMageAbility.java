package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class HoradricMageAbility extends HeroTowerBuffAbility {

    private static final float damageMalus = -0.2f;
    private static final float attackSpeedMalus = -0.1f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addAddedRelativeBaseDamage(damageMalus);
        tower.addAttackSpeed(attackSpeedMalus);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit == getUnit()) {
            unit.getWizard().requiredTransmuteAmount = 3;
        } else {
            super.onUnitAdded(unit);
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Mage in a box!";
    }

    @Override
    public String getDescription() {
        return format.percentWithSignAndUnit(damageMalus) + " damage\n" +
                format.percentWithSignAndUnit(attackSpeedMalus) + " attack speed\n" +
                "You only need 3 items/towers to combine";
    }
}
