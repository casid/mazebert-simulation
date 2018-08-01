package com.mazebert.simulation.messages;

import com.mazebert.simulation.commands.Command;
import org.jusecase.bitnet.message.BitMessage;

import java.util.Collections;
import java.util.List;

public strictfp class Turn extends BitMessage {
    public int playerId;
    public int turnNumber;
    public int hash;
    public List<Command> commands = Collections.emptyList();

    public transient boolean processed;
}
