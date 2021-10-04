package com.mazebert.simulation.stash;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.Element;
import com.mazebert.simulation.units.towers.Tower;
import com.mazebert.simulation.units.towers.TowerType;

import java.util.EnumMap;
import java.util.EnumSet;

public strictfp class TowerStash extends Stash<Tower> {
    private EnumSet<Element> elements;

    @SuppressWarnings("unchecked")
    public TowerStash() {
        super(new EnumMap(TowerType.class), new EnumMap(TowerType.class), new EnumMap(TowerType.class));
    }

    @Override
    public TowerType[] getPossibleDrops() {
        return TowerType.getValues();
    }

    @Override
    public CardCategory getCardCategory() {
        return CardCategory.Tower;
    }

    public void setElements(EnumSet<Element> elements) {
        this.elements = elements;
        updateCardByDropRarity();
    }

    @Override
    protected boolean isDropable(Tower card) {
        if (super.isDropable(card)) {
            if (elements != null && elements.contains(card.getElement())) {
                return true;
            }
            if (card.getElement() == Element.Unknown) {
                return true;
            }
        }

        return false;
    }

    public boolean isElementResearched(Element element) {
        return elements != null && elements.contains(element);
    }

    public boolean researchElement(Element element) {
        if (isElementResearched(element)) {
            return false;
        } else {
            EnumSet<Element> clone = elements.clone();
            clone.add(element);

            setElements(clone);

            return true;
        }
    }
}
