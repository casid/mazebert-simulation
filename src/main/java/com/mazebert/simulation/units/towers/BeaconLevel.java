package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.Ability;
import com.mazebert.simulation.units.potions.PotionType;

public strictfp class BeaconLevel extends Ability<Tower> {

    @Override
    public boolean isVisibleToUser() {
        return true;
    }

    @Override
    public String getTitle() {
        return "The Sky is the Limit";
    }

    @Override
    public String getDescription() {
        return "Has no level cap.\nWhenever Beacon gains a level above 99, it produces a " + format.card(PotionType.LeuchtFeuer) + " potion.";
    }

    @Override
    public String getIconFile() {
        return "leuchtfeuer_512";
    }
}
