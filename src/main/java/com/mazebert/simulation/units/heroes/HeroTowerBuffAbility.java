package com.mazebert.simulation.units.heroes;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public abstract strictfp class HeroTowerBuffAbility extends Ability<Hero> implements OnUnitAddedListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    @Override
    protected void initialize(Hero unit) {
        super.initialize(unit);
        simulationListeners.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Hero unit) {
        simulationListeners.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit instanceof Tower && getUnit().getPlayerId() == unit.getPlayerId()) {
            buffTower((Tower) unit);
        }
    }

    protected abstract void buffTower(Tower tower);
}
