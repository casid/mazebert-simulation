package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

public interface OnAttackOrderedListener {
    void onAttackRequested(Wizard wizard, Creep creep);
}
