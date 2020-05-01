package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CardDustVitalAbility extends StackableAbility<Tower> {
    private static final float health = 0.2f;

    @Override
    public void addStack() {
        super.addStack();

        getUnit().getWizard().addHealth(getHealth());
    }

    private float getHealth() {
        if (getUnit().getPotionEffectiveness() == 1) {
            return health;
        }
        return health * getUnit().getPotionEffectiveness();
    }

    @Override
    protected void updateStacks() {
        // unused
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The Essence of Vitality";
    }

    @Override
    public String getLevelBonus() {
        return format.percentWithSignAndUnit(health) + " player health.";
    }
}
