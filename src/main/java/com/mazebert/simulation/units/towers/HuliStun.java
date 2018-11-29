package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.StunAbility;

public strictfp class HuliStun extends StunAbility {

    public HuliStun() {
        setChance(0.2f);
        setDuration(0.2f);
    }

    @Override
    public String getStunText() {
        return "Slipped on banana!";
    }

    @Override
    public int getStunColor() {
        return 0xffff00;
    }

    @Override
    public boolean isVisibleToUser() {
        return false;
    }
}
