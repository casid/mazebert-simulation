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
        int timeModifier = (int) simulation.getRawTimeModifier();

        switch (timeModifier) {
            case 1:
                setTimeModifier(simulation, 2);
                break;
            case 2:
                setTimeModifier(simulation, 3);
                break;
            case 3:
                setTimeModifier(simulation, 4);
                break;
            default:
                setTimeModifier(simulation, 1);
                break;
        }
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
        return "Make time pass 2, 3 or 4 times faster.";
    }
}
