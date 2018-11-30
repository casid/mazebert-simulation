package com.mazebert.simulation.units.towers;

import com.mazebert.simulation.units.abilities.SplashAbility;

public class BaluSplash extends SplashAbility {
    public BaluSplash() {
        super(3, 0.5f);
    }

    @Override
    public String getTitle() {
        return "Earthquake";
    }

    @Override
    public String getDescription() {
        return "Creeps in " + getRange() + " range around Balu's target receive " + format.percent(getDamageFactor()) + "% splash damage.";
    }

    @Override
    public String getIconFile() {
        return "0065_claws_512";
    }
}
