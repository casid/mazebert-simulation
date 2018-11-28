package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class CardDustCritAbility extends StackableAbility<Tower> {
    private int currentBonus;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        addBonus();
    }

    @Override
    protected void dispose(Tower unit) {
        removeBonus();
        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        removeBonus();
        addBonus();
    }

    private void removeBonus() {
        if (currentBonus > 0) {
            getUnit().addMulticrit(-currentBonus);
            currentBonus = 0;
        }
    }

    private void addBonus() {
        if (currentBonus == 0) {
            currentBonus = getStackCount();
            getUnit().addMulticrit(currentBonus);
        }
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Critical Dust";
    }

    @Override
    public String getDescription() {
        return "+ 1 multicrit";
    }
}
