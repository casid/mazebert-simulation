package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnLevelChangedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class TowerLevelsQuest extends Quest implements OnLevelChangedListener, OnUnitAddedListener, OnUnitRemovedListener {
    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    public TowerLevelsQuest() {
        super(QuestReward.Medium, 400);
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        unitGateway.forEach(Tower.class, tower -> tower.onLevelChanged.add(this));
        simulationListeners.onUnitAdded.add(this);
        simulationListeners.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        unitGateway.forEach(Tower.class, tower -> tower.onLevelChanged.remove(this));
        simulationListeners.onUnitAdded.remove(this);
        simulationListeners.onUnitRemoved.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onLevelChanged(Unit unit, int oldLevel, int newLevel) {
        addAmount(newLevel - oldLevel);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (unit instanceof Tower && unit.getWizard() == getUnit()) {
            ((Tower) unit).onLevelChanged.add(this);
        }
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Tower && unit.getWizard() == getUnit()) {
            ((Tower) unit).onLevelChanged.remove(this);
        }
    }

    @Override
    public String getSinceVersion() {
        return "1.0.0";
    }

    @Override
    public String getTitle() {
        return "The wisdom of towers";
    }

    @Override
    public String getDescription() {
        return "Gain " + getRequiredAmount() + " tower levels!";
    }
}
