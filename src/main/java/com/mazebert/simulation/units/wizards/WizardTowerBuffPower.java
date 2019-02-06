package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class WizardTowerBuffPower extends WizardPower implements OnUnitAddedListener {
    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        Sim.context().simulationListeners.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        Sim.context().simulationListeners.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit instanceof Tower && unit.getPlayerId() == getUnit().getPlayerId()) {
            buffTower((Tower)unit);
        }
    }

    protected abstract void buffTower(Tower tower);
}
