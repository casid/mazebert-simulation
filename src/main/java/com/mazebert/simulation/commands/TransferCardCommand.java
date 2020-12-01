package com.mazebert.simulation.commands;

import com.mazebert.simulation.CardCategory;
import com.mazebert.simulation.CardType;

public strictfp class TransferCardCommand extends Command {
    public int toPlayerId;
    public CardCategory cardCategory;
    public CardType cardType;

    @Override
    public boolean isValid() {
        return cardCategory != null && cardType != null;
    }
}
