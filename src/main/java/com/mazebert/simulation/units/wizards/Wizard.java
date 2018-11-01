package com.mazebert.simulation.units.wizards;

import com.mazebert.simulation.Card;
import com.mazebert.simulation.units.Unit;

import java.util.ArrayList;
import java.util.List;

public strictfp class Wizard extends Unit {
    private final List<Card> hand = new ArrayList<>();
    private final List<Card> pile = new ArrayList<>();

    public List<Card> getHand() {
        return hand;
    }
}
