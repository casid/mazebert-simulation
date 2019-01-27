package com.mazebert.simulation.commands;

import com.mazebert.simulation.Difficulty;
import com.mazebert.simulation.Sim;

import java.util.UUID;

public strictfp class InitGameCommand extends Command {
    public int version = Sim.version;
    public UUID gameId;
    public int rounds;
    public Difficulty difficulty = Difficulty.Easy;
}
