package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class VeledaCreateProphecy extends Ability<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnRoundStartedListener {

    public static final float CHANCE = 0.09f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final LootSystem lootSystem = Sim.context().lootSystem;

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
        simulationListeners.onRoundStarted.add(this);
        Sim.context().prophecySystem.setProphecyTower(unit.getPlayerId(), (Tower) unit);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        Sim.context().prophecySystem.setProphecyTower(unit.getPlayerId(), null);
        simulationListeners.onRoundStarted.remove(this);
    }

    @Override
    public void onRoundStarted(Wave wave) {
        if (getUnit().isAbilityTriggered(CHANCE)) {
            Wizard wizard = getUnit().getWizard();
            ItemType prophecyItem = lootSystem.addRandomDrop(wizard, wizard.itemStash.getProphecyItems(), getUnit().getLevel());

            if (prophecyItem != null && simulationListeners.areNotificationsEnabled()) {
                simulationListeners.showNotification(getUnit(), "Prophecy created", 0xe86ef7);
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getIconFile() {
        return "prophecy";
    }

    @Override
    public String getTitle() {
        return format.prophecyTitle("Voeluspa");
    }

    @Override
    public String getDescription() {
        return format.prophecyDescription("Each round there is a " + format.percent(CHANCE) + "% chance " + format.card(TowerType.Veleda) + " creates a prophecy item.");
    }
}
