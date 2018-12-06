package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;

public strictfp class MoneyBinInterest extends Ability<Tower> implements OnUnitAddedListener, OnUnitRemovedListener {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
        unit.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        unit.onUnitRemoved.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        unit.getWizard().interestBonus += 0.01f;
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        unit.getWizard().interestBonus -= 0.01f;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Capitalist";
    }

    @Override
    public String getDescription() {
        return "The interest rate is increased by 1%.";
    }

    @Override
    public String getIconFile() {
        return "hoard_512";
    }
}
