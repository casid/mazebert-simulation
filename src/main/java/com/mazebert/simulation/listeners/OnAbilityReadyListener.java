package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.abilities.ActiveAbility;

public interface OnAbilityReadyListener {
    void onAbilityReady(ActiveAbility ability);
}
