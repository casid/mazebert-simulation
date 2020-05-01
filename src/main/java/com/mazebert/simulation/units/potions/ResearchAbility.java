package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Sim;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.wizards.Wizard;

public strictfp class ResearchAbility extends Ability<Tower> {
    private final Element element;

    public ResearchAbility(Element element) {
        this.element = element;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        Wizard wizard = unit.getWizard();
        if (wizard.towerStash.researchElement(element)) {
            if (Sim.context().simulationListeners.areNotificationsEnabled()) {
                Sim.context().simulationListeners.showNotification(wizard, "Element Research: " + format.element(element));
            }
        }
    }

    public Element getElement() {
        return element;
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Expand Your Horizons!";
    }

    @Override
    public String getDescription() {
        return "Drink this elixir, to research the " + format.element(element) + " element.";
    }
}
