package com.mazebert.simulation.commands;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;

public class CraftCardsCommand extends Command {
    public CardCategory cardCategory;
    public CardType cardType;
    public boolean all;
}
