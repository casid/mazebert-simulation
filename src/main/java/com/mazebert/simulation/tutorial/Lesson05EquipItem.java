package com.mazebert.simulation.tutorial;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.listeners.OnItemEquippedListener;
import com.mazebert.simulation.units.items.BabySword;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.items.ItemType;
import com.mazebert.simulation.units.towers.Dandelion;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class Lesson05EquipItem extends Lesson implements OnItemEquippedListener {

    private final UnitGateway unitGateway = Sim.context().unitGateway;

    private Dandelion dandelion;

    @Override
    public void initialize(Wizard wizard) {
        super.initialize(wizard);
        wizard.itemStash.add(ItemType.BabySword);

        dandelion = unitGateway.findUnit(Dandelion.class, wizard.playerId);
        dandelion.onItemEquipped.add(this);
    }

    @Override
    public void dispose(Wizard wizard) {
        dandelion.onItemEquipped.remove(this);
        super.dispose(wizard);
    }

    @Override
    public void onItemEquipped(Tower tower, int index, Item oldItem, Item newItem) {
        if (newItem instanceof BabySword) {
            finish();
        }
    }
}
