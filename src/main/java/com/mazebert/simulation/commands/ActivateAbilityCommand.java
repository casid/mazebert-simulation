package com.mazebert.simulation.commands;

import com.mazebert.simulation.units.abilities.ActiveAbilityType;

public strictfp class ActivateAbilityCommand extends Command {
    public ActiveAbilityType abilityType;
    public int towerX;
    public int towerY;
}
