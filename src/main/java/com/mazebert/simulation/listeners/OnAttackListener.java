package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.creeps.Creep;

public interface OnAttackListener {
    void onAttack(Creep target);
}
