package com.mazebert.simulation.units.towers;

import com.mazebert.java8.Consumer;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.gateways.UnitGateway;
import com.mazebert.simulation.units.abilities.CooldownUnitAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class KiwiKick extends CooldownUnitAbility<Tower> implements Consumer<Creep> {

    private final UnitGateway unitGateway = Sim.context().unitGateway;
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private boolean didAttack;

    @Override
    protected boolean onCooldownReached() {
        didAttack = false;
        unitGateway.forEachInRange(getUnit().getX(), getUnit().getY(), getUnit().getRange(), Creep.class, this);

        if (didAttack && simulationListeners.areNotificationsEnabled()) {
            simulationListeners.showNotification(getUnit(), "Roundhouse Kick!", 0xffab00);
        }

        return didAttack;
    }

    @Override
    public void accept(Creep creep) {
        if (creep.isPartOfGame()) {
            getUnit().onAttack.dispatch(creep);
            didAttack = true;
        }
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Roundhouse Kick";
    }

    @Override
    public String getDescription() {
        return "Kiwi's roundhouse kick hurts all creeps in range.";
    }

    @Override
    public String getIconFile() {
        return "kiwi_kick_512";
    }
}
