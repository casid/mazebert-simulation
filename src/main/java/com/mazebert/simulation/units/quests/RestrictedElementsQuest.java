package com.mazebert.simulation.units.quests;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.SimulationListeners;
import com.mazebert.simulation.listeners.OnGameWonListener;
import com.mazebert.simulation.listeners.OnUnitAddedListener;
import com.mazebert.simulation.units.Unit;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

import java.util.EnumSet;

public abstract strictfp class RestrictedElementsQuest extends Quest implements OnUnitAddedListener, OnGameWonListener {
    private final SimulationListeners simulationListeners = Sim.context().simulationListeners;

    private final EnumSet<Element> allowedElements;

    public RestrictedElementsQuest(EnumSet<Element> allowedElements) {
        super(allowedElements.size() == 1 ? QuestReward.Big : QuestReward.Medium, 1);
        this.allowedElements = allowedElements;
    }

    @Override
    protected void initialize(Wizard unit) {
        super.initialize(unit);
        simulationListeners.onUnitAdded.add(this);
        simulationListeners.onGameWon.add(this);
    }

    @Override
    protected void dispose(Wizard unit) {
        simulationListeners.onUnitAdded.remove(this);
        simulationListeners.onGameWon.remove(this);
        super.dispose(unit);
    }

    @Override
    public void onUnitAdded(Unit unit) {
        if (isForbiddenElement(unit)) {
            questFailed();
        }
    }

    @SuppressWarnings("RedundantIfStatement")
    private boolean isForbiddenElement(Unit unit) {
        if (unit.getWizard() != getUnit()) {
            return false;
        }

        if (!(unit instanceof Tower)) {
            return false;
        }

        Tower tower = (Tower)unit;
        if (allowedElements.contains(tower.getElement()) || tower.getElement() == Element.Unknown) {
            return false;
        }

        return true;
    }

    @Override
    public void onGameWon() {
        addAmount(1);
    }

    @Override
    public boolean isHidden() {
        return allowedElements.size() == 1;
    }

    @Override
    public String getDescription() {
        return format.listing("Win a game with ", allowedElements, format::element, " towers only.");
    }
}
