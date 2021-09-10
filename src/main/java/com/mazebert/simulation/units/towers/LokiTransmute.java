package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.Sim;
import com.mazebert.simulation.listeners.OnProphecyFulfilledListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.listeners.OnUnitRemovedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.items.Item;
import com.mazebert.simulation.units.wizards.Wizard;
import com.mazebert.simulation.usecases.TransmuteCards;

public strictfp class LokiTransmute extends Ability<Tower> implements OnUnitAddedListener, OnUnitRemovedListener, OnProphecyFulfilledListener {

    private static final int multiluck = 1;

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.onUnitAdded.add(this);
        unit.onUnitRemoved.add(this);
        unit.addMultiluck(multiluck);
    }

    @Override
    protected void dispose(Tower unit) {
        unit.addMultiluck(-multiluck);
        unit.onUnitAdded.remove(this);
        unit.onUnitRemoved.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        unit.getWizard().onProphecyFulfilled.add(this);
    }

    @Override
    public void onUnitRemoved(Unit unit) {
        unit.getWizard().onProphecyFulfilled.remove(this);
    }

    @Override
    public void onProphecyFulfilled(Wizard wizard, Item prophecy) {
        TransmuteCards transmuteCards = Sim.context().commandExecutor.getTransmuteCards();
        transmuteCards.impossibleLokiTransmute(wizard, prophecy);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Tricks up his sleeve";
    }

    @Override
    public String getDescription() {
        return format.card(TowerType.Loki) + " transmutes fulfilled prophecies.";
    }

    @Override
    public String getLevelBonus() {
        return "+" + multiluck + " multiluck";
    }

    @Override
    public String getIconFile() {
        return "prophecy";
    }

}
