package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.wizards.Wizard;
import org.jusecase.signals.Signal;

public strictfp class OnWizardHealthChanged extends Signal<OnWizardHealthChangedListener> {
    public void dispatch(Wizard wizard, double oldHealth, double newHealth) {
        for (OnWizardHealthChangedListener listener : this) {
            listener.onHealthChanged(wizard, oldHealth, newHealth);
        }
    }
}
