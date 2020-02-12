package com.mazebert.simulation.units.potions;

import com.mazebert.simulation.Element;
import com.mazebert.simulation.Rarity;

public strictfp abstract class Research extends Potion {

    private final Element element;

    public Research(ResearchAbility researchAbility) {
        super(researchAbility);
        element = researchAbility.getElement();
    }

    @Override
    public Element getElement() {
        return element;
    }

    @Override
    public Rarity getRarity() {
        return Rarity.Unique;
    }

    @Override
    public int getItemLevel() {
        return 1;
    }

    @Override
    public boolean isDropable() {
        return false;
    }
}
