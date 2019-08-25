package com.mazebert.simulation.commands;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;

public strictfp class AutoTransmuteCardsCommand extends Command {
    public CardCategory cardCategory;
    public CardType cardType;
    public boolean remove;
}
