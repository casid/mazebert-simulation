package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnTowerBuiltListener;
import com.mazebert.simulation.stash.ItemStash;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.items.Mjoelnir;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class ThorMjoelnir extends Ability<Tower> implements OnTowerBuiltListener {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onTowerBuilt.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onTowerBuilt.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onTowerBuilt(Tower oldTower) {
        Item item = createItem();
        if (item != null) {
            dropPreviousItem();
            if (item.isUniqueInstance()) {
                getUnit().getWizard().itemStash.setUnique(item.getType(), item);
            }
            getUnit().setItem(0, item);
        }
    }


    private Item createItem() {
        if (getUnit().hasItem(ItemType.Mjoelnir)) {
            return null;
        }

        Wizard wizard = getUnit().getWizard();
        ItemStash itemStash = wizard.itemStash;

        Item fromInventory = itemStash.remove(ItemType.Mjoelnir);
        if (fromInventory != null) {
            return fromInventory;
        }

        Tower fromTower = unitGateway.findUnit(Tower.class, tower -> tower.getWizard() == wizard && tower.hasItem(ItemType.Mjoelnir));
        if (fromTower != null) {
            return fromTower.removeItem(ItemType.Mjoelnir);
        }

        return new Mjoelnir();
    }

    private void dropPreviousItem() {
        Item previousItem = getUnit().getItem(0);
        if (previousItem != null) {
            getUnit().getWizard().itemStash.add(previousItem.getType());
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Still Worthy!";
    }

    @Override
    public String getDescription() {
        return "When built, calls " + format.card(ItemType.Mjoelnir) + " to his side and never gives it away again. Each attack casts deadly lightning from the sky. " + format.card(ItemType.Mjoelnir) + " effects are doubled.";
    }

    @Override
    public String getIconFile() {
        return "mjoelnir-512";
    }
}
