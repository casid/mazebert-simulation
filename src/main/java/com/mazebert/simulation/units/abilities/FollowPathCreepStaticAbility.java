package com.mazebert.simulation.units.abilities;

public strictfp class FollowPathCreepStaticAbility extends FollowPathCreepAbility {
    @Override
    protected boolean isPossibleToWalk() {
        return false;
    }
}
