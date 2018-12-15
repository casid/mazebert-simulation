package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public strictfp class HuliAttack extends AttackAbility {
    private boolean enabled = true;

    @Override
    protected boolean onCooldownReached() {
        if (enabled) {
            return super.onCooldownReached();
        }
        return true;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
