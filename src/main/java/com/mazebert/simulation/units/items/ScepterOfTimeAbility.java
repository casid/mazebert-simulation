package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Simulation;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.abilities.ActiveAbility;

public strictfp class ScepterOfTimeAbility extends ActiveAbility {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public float getReadyProgress() {
        return 1.0f;
    }

    @Override
    public void activate() {
        Simulation simulation = Sim.context().simulation;
        int timeModifier = (int) simulation.getRawTimeModifier() + 1;
        if (timeModifier > getMaxTimeModifier()) {
            timeModifier = 1;
        }

        setTimeModifier(simulation, timeModifier);
    }

    private int getMaxTimeModifier() {
        if (Sim.context().version >= Sim.v24) {
            return 5;
        }
        return 4;
    }

    void setTimeModifier(Simulation simulation, int timeModifier) {
        simulation.setTimeModifier(timeModifier);

        if (simulationListeners.areNotificationsEnabled()) {
            String notification = createNotification(timeModifier);
            unitGateway.forEachWizard(w -> simulationListeners.showNotification(w, notification));
        }
    }

    private String createNotification(int timeModifier) {
        if (timeModifier == 1) {
            return format.playerName(getUnit().getWizard()) + " changed time back to normal.";
        }
        return format.playerName(getUnit().getWizard()) + " made time pass " + timeModifier + "x faster.";
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Ancient perception (Active ability)";
    }

    @Override
    public String getLevelBonus() {
        if (Sim.context().version >= Sim.v24) {
            return "Make time pass faster.";
        } else {
            return "Make time pass 2, 3 or 4 times faster.";
        }
    }
}
