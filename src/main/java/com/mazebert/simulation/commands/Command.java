package com.mazebert.simulation.commands;

public strictfp abstract class Command {
    public transient int playerId;
    public transient int turnNumber;
}
