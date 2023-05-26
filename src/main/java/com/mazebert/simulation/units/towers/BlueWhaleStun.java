package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.StunAbility;
import com.mazebert.simulation.units.creeps.Creep;

public strictfp class BlueWhaleStun extends StunAbility {

    public BlueWhaleStun() {
        setChance(0.8f);
        setDuration(0.4f);
    }

    @Override
    public void onDamage(Object origin, Creep target, double damage, int multicrits) {
        if (multicrits > 0) {
            super.onDamage(origin, target, damage, multicrits);
        }
    }

    @Override
    protected boolean isOriginalDamage(Object origin) {
        if (origin instanceof BlueWhaleSplash) {
            return true;
        }

        return super.isOriginalDamage(origin);
    }

    @Override
    public String getTitle() {
        return "Splash on the head";
    }

    @Override
    public String getIconFile() {
        return "wood_pieces_512"; // TODO
    }

    @Override
    public String getDescription() {
        return "Critical damage has a " + format.percent(getChance()) + "% chance to stun creeps for " + getDuration() + "s.";
    }
}
