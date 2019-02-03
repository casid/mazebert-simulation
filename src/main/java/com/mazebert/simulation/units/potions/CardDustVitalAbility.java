package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CardDustVitalAbility extends StackableAbility<Tower> {
    private static final float health = 0.2f;

    @Override
    public void addStack() {
        super.addStack();
        getUnit().getWizard().addHealth(health);
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
        return "Vital Dust";
    }

    @Override
    public String getDescription() {
        return "+ " + format.percent(health) + "% player health";
    }
}
