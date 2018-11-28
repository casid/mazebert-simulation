package com.mazebert.simulation.listeners;

import com.mazebert.simulation.units.potions.Potion;
import com.mazebert.simulation.units.towers.Tower;

public interface OnPotionConsumedListener {
    void onPotionConsumed(Tower tower, Potion potion);
}
