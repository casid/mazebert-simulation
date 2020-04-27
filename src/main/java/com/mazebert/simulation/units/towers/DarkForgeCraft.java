package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Rarity;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.Wave;
import com.mazebert.simulation.listeners.OnRoundStartedListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.listeners.OnWaveFinishedListener;
import com.mazebert.simulation.plugins.random.RandomPlugin;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.systems.LootSystem;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

public strictfp class DarkForgeCraft extends Ability<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnWaveFinishedListener, OnRoundStartedListener {

    public static final float CHANCE = 0.1f;

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final RandomPlugin randomPlugin = Sim.context().randomPlugin;
    private final LootSystem lootSystem = Sim.context().lootSystem;
    private final int version = Sim.context().version;

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
        if (Sim.context().version < Sim.v13) {
            simulationListeners.onWaveFinished.add(this);
        } else {
            simulationListeners.onRoundStarted.add(this);
        }
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        if (Sim.context().version < Sim.v13) {
            simulationListeners.onWaveFinished.remove(this);
        } else {
            simulationListeners.onRoundStarted.remove(this);
        }
    }

    @Override
    public void onWaveFinished(Wave wave) {
        craft();
    }

    @Override
    public void onRoundStarted(Wave wave) {
        craft();
    }

    private void craft() {
        if (getUnit().isAbilityTriggered(CHANCE)) {
            List<ItemType> possibleItems = calculatePossibleItems();

            if (!possibleItems.isEmpty()) {
                ItemType itemDrop = randomPlugin.get(possibleItems);
                Wizard wizard = getUnit().getWizard();

                if (version >= Sim.v14) {
                    lootSystem.addToStash(wizard, null, wizard.itemStash, itemDrop);
                } else {
                    wizard.itemStash.add(itemDrop, true);
                }

                if (simulationListeners.areNotificationsEnabled()) {
                    simulationListeners.showNotification(getUnit(), "Dark item forged", 0xa800ff);
                }
            }
        }
    }

    protected List<ItemType> calculatePossibleItems() {
        int itemLevel = getUnit().getLevel();

        Wizard wizard = getUnit().getWizard();
        ItemStash itemStash = wizard.itemStash;
        EnumSet<ItemType> darkItems = itemStash.getDarkItems();
        List<ItemType> possibleItems = new ArrayList<>(darkItems.size());
        for (ItemType darkItem : darkItems) {
            if (darkItem.instance().getRarity() == Rarity.Legendary && !wizard.foilItems.contains(darkItem)) {
                continue;
            }
            if (darkItem.instance().getItemLevel() <= itemLevel && !itemStash.isUniqueAlreadyDropped(darkItem)) {
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
        return "Dark Items";
    }

    @Override
    public String getDescription() {
        return "Each round, the Forge has a " + format.percent(CHANCE) + "% chance to create a dark item. The item level depends on the level of the Forge.";
    }

    @Override
    public String getIconFile() {
        return "0094_One_Handed_Hammer_512";
    }
}
