package com.mazebert.simulation.commands;

import java.util.UUID;

public strictfp class BuildTowerCommand extends Command {
    public UUID cardId;
    public int x;
    public int y;

    public transient Runnable onError;
    public transient Runnable onComplete;
}
