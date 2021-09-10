package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.wizards.Wizard;

public interface OnProphecyFulfilledListener {
    void onProphecyFulfilled(Wizard wizard, Item prophecy);
}
