package com.mazebert.simulation.units.items;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.Simulation;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.abilities.CooldownActiveAbility;

public strictfp class ScepterOfTimeAbility extends CooldownActiveAbility {

    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;
    private final UnitGateway unitGateway = Sim.context().unitGateway;

    @Override
    public float getCooldown() {
        return 1.0f;
    }

    @Override
    public void activate() {
        Simulation simulation = Sim.context().simulation;
        int timeModifier = (int) simulation.getTimeModifier();

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
        }
    }

    void setTimeModifier(Simulation simulation, int timeModifier) {
        simulation.setTimeModifier(timeModifier);

        if (simulationListeners.areNotificationsEnabled()) {
            String notification = getUnit().getWizard().name + " made time pass " + timeModifier + "x faster.";
            unitGateway.forEachWizard(w -> simulationListeners.showNotification(w, notification));
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Ancient perception";
    }

    @Override
    public String getDescription() {
        return "It can change the flow of time.";
    }
}
