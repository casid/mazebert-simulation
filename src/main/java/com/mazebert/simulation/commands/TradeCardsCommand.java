package com.mazebert.simulation.commands;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;

public class TradeCardsCommand extends Command {
    public CardCategory cardCategory;
    public CardType cardType;
    public boolean all;
}
