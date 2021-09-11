package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class ThorAttack extends AttackAbility {
    private boolean holdBack;

    @Override
    protected boolean onCooldownReached() {
        if (holdBack) {
            return false;
        }
        return super.onCooldownReached();
    }

    public void toggleHoldBack() {
        holdBack = !holdBack;
    }

    public boolean isHoldBack() {
        return holdBack;
    }
}
