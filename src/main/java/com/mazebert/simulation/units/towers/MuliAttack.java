package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.AttackAbility;

public class MuliAttack extends AttackAbility {
    private int bananas;

    @Override
    protected boolean onCooldownReached() {
        if (bananas > 0) {
            if (super.onCooldownReached()) {
                --bananas;
                return true;
            }
        }
        return false;
    }

    public int getBananas() {
        return bananas;
    }

    public void addBananas(int amount) {
        bananas += amount;
    }
}
