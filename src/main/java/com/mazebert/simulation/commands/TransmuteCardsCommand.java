package com.mazebert.simulation.commands;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;

public strictfp class TransmuteCardsCommand extends Command {
    public CardCategory cardCategory;
    public CardType cardType;
    public boolean all;
    public int amountToKeep;

    public transient boolean automatic;
}
