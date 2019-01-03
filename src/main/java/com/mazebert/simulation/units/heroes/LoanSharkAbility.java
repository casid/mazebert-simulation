package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Context;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class LoanSharkAbility extends HeroTowerBuffAbility {

    private static final int startingGold = 5000;
    private static final float interestMalus = -0.01f;
    private static final float goldMalus = -0.5f;

    @Override
    protected void buffTower(Tower tower) {
        tower.addGoldModifer(goldMalus);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit == getUnit()) {
            unit.getWizard().addGold(startingGold);
            unit.getWizard().interestBonus += interestMalus;
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
        return "Money for nothing!";
    }

    @Override
    public String getDescription() {
        return "+" + format.gold(startingGold) + " starting " + Context.currency.pluralLowercase + "\n" +
                format.percentWithSignAndUnit(interestMalus) + "\n" +
                format.percentWithSignAndUnit(goldMalus) + " " + Context.currency.pluralLowercase;
    }
}
