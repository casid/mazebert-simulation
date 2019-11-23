package com.mazebert.simulation.units.items;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class GuardLanceAbility extends StackableAbility<Tower> {
    private static final int ABSOLUTE_DAMAGE = 2;
    private static final float RELATIVE_DAMAGE = 0.2f;

    private int currentAbsoluteDamage;
    private float currentRelativeDamage;

    @Override
    protected void updateStacks() {
        getUnit().addAddedAbsoluteBaseDamage(-currentAbsoluteDamage);
        currentAbsoluteDamage = ABSOLUTE_DAMAGE * getStackCount();
        getUnit().addAddedAbsoluteBaseDamage(currentAbsoluteDamage);

        getUnit().addAddedRelativeBaseDamage(-currentRelativeDamage);
        currentRelativeDamage = RELATIVE_DAMAGE * getStackCount();
        getUnit().addAddedRelativeBaseDamage(currentRelativeDamage);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Guard wannabe";
    }

    @Override
    public String getDescription() {
        return "The carrier becomes a guard.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + ABSOLUTE_DAMAGE + " base damage\n" + format.percentWithSignAndUnit(RELATIVE_DAMAGE) + " damage";
    }
}
