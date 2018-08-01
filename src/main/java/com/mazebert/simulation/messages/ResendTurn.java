package com.mazebert.simulation.messages;

import org.jusecase.bitnet.message.BitMessage;

public strictfp class ResendTurn extends BitMessage {
    public int turnNumber;
}
