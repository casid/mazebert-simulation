package com.mazebert.simulation.commands;

import java.util.UUID;

public strictfp class InitGameCommand extends Command {
    public UUID gameId;
    public int rounds;
}
