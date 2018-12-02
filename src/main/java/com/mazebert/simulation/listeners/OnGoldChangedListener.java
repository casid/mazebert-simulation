package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.wizards.Wizard;

public interface OnGoldChangedListener {
    void onGoldChanged(Wizard wizard, long oldGold, long newGold);
}
