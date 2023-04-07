package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.listeners.OnWizardHealthChangedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class HydraMultishot extends AttackAbility implements OnUnitAddedListener, OnUnitRemovedListener, OnWizardHealthChangedListener {

    private double accumulatedLostHealth = 0.0;

    public HydraMultishot() {
        super(3, true);
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);

        unit.onUnitAdded.add(this);
        unit.onUnitRemoved.add(this);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.onUnitRemoved.remove(this);
        unit.onUnitAdded.remove(this);

        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        unit.getWizard().onHealthChanged.add(this);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        unit.getWizard().onHealthChanged.remove(this);
    }

    @Override
    public void onHealthChanged(Unit unit, double oldHealth, double newHealth) {
        double lostHealth = oldHealth - newHealth;
        if (lostHealth <= 0.0) {
            return;
        }

        accumulatedLostHealth += lostHealth;

        while (accumulatedLostHealth > 0.1) {
            accumulatedLostHealth -= 0.1;

            setTargets(getTargets() + 1);

            if (Sim.context().simulationListeners.areNotificationsEnabled()) {
                Sim.context().simulationListeners.showNotification(this.getUnit(), "Fell Regrowth");
            }
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Fell Regrowth";
    }

    @Override
    public String getDescription() {
        return "Each time the wizard loses 10% of total health, Hydra loses a head and regrows two.";
    }

    @Override
    public String getIconFile() {
        return "hydra_regrowth";
    }

    public int getHeadCount() {
        return getTargets();
    }
}
