package com.mazebert.simulation.listeners;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.units.creeps.Creep;
import com.mazebert.simulation.units.wizards.Wizard;

public interface OnCardDroppedListener {
    void onCardDropped(Wizard wizard, Creep creep, Card card);
}
