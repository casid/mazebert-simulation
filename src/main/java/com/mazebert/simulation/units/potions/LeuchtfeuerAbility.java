package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnPotionEffectivenessChangedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.StackableAbility;
import com.mazebert.simulation.units.towers.Beacon;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

public strictfp class LeuchtfeuerAbility extends StackableAbility<Tower> implements OnPotionEffectivenessChangedListener, OnUnitRemovedListener {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private int maxLevels;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        unit.onPotionEffectivenessChanged.add(this);
        simulationListeners.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onPotionEffectivenessChanged.remove(this);
        simulationListeners.onUnitRemoved.remove(this);

        super.dispose(unit);
    }

    @Override
    protected void updateStacks() {
        removeStacks();
        addStacks();
    }

    private void removeStacks() {
        getUnit().addMaxLevel(-maxLevels);
        maxLevels = 0;
    }

    private void addStacks() {
        if (getUnit().getElement() == Element.Light && unitGateway.hasUnits(Beacon.class, u -> u.getPlayerId() == getUnit().getPlayerId())) {
            maxLevels = StrictMath.round(getStackCount() * getUnit().getPotionEffectiveness());
            getUnit().addMaxLevel(maxLevels);
        }
    }

    @Override
    public boolean isPermanent() {
        return true;
    }

    @Override
    public void onPotionEffectivenessChanged(Tower tower) {
        updateStacks();
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (unit instanceof Beacon) {
            updateStacks();
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getDescription() {
        return "Works for any " + format.element(Element.Light) + " tower, only as long as " + format.card(TowerType.Beacon) + " is still on the map.";
    }

    @Override
    public String getLevelBonus() {
        return "+1 to max tower level";
    }
}
