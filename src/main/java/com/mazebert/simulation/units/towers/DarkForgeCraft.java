package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.listeners.OnWaveFinishedListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.ItemType;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public strictfp class DarkForgeCraft extends Ability<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnWaveFinishedListener {

    public static final float CHANCE = 0.01f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;

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
        simulationListeners.onWaveFinished.add(this);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        simulationListeners.onWaveFinished.remove(this);
    }

    @Override
    public void onWaveFinished(Wave wave) {
        if (getUnit().isAbilityTriggered(CHANCE)) {
            List<ItemType> possibleItems = calculatePossibleItems();

            if (!possibleItems.isEmpty()) {
                ItemType itemDrop = randomPlugin.get(possibleItems);
                getUnit().getWizard().itemStash.add(itemDrop);

                if (simulationListeners.areNotificationsEnabled()) {
                    simulationListeners.showNotification(getUnit(), "Dark item forged", 0xa800ff);
                }
            }
        }
    }

    protected List<ItemType> calculatePossibleItems() {
        // TODO solve dark, legendary item drops!

        // TODO dark, unique cards only once!

        int itemLevel = getUnit().getLevel();

        EnumSet<ItemType> darkItems = getUnit().getWizard().itemStash.getDarkItems();
        List<ItemType> possibleItems = new ArrayList<>(darkItems.size());
        for (ItemType darkItem : darkItems) {
            if (darkItem.instance().getItemLevel() <= itemLevel) {
                possibleItems.add(darkItem);
            }
        }

        return possibleItems;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Dark items";
    }

    @Override
    public String getDescription() {
        return "Every round there is a " + format.percent(CHANCE) + "% chance to create a dark item. The item level depends on the level of the Forge.";
    }

    @Override
    public String getIconFile() {
        return "0094_One_Handed_Hammer_512";
    }
}
