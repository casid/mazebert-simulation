package com.mazebert.simulation.units.abilities;

import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.towers.Tower;

public strictfp abstract class GainItemOnBuildAbility extends Ability<Tower> implements OnUnitAddedListener {
    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitAdded.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        dropPreviousItem();
        addItem();
    }

    protected abstract Item createItem();

    private void addItem() {
        Item item = createItem();
        if (item.isUniqueInstance()) {
            getUnit().getWizard().itemStash.setUnique(item.getType(), item);
        }
        getUnit().setItem(0, item);
    }

    private void dropPreviousItem() {
        Item previousItem = getUnit().getItem(0);
        if (previousItem != null) {
            getUnit().getWizard().itemStash.add(previousItem.getType());
        }
    }
}
