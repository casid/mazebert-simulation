package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.towers.Tower;

public strictfp class ResearchAbility extends Ability<Tower> {
    private final Element element;

    public ResearchAbility(Element element) {
        this.element = element;
    }

    @Override
    protected void initialize(Tower unit) {
        super.initialize(unit);
        unit.getWizard().towerStash.researchElement(element);
    }

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "Expand your horizon!";
    }

    @Override
    public String getDescription() {
        return "Drink this elixir, to research the " + format.element(element) + " element.";
    }
}
