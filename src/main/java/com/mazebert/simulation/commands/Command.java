package com.mazebert.simulation.commands;

public strictfp abstract class Command {
    public transient int playerId;
    public transient int turnNumber;
    public transient boolean excludedFromReplay;
    public transient boolean internal;

    public abstract boolean isValid();
}
